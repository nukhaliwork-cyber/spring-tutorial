import { Test, TestingModule } from '@nestjs/testing';
import { INestApplication, ValidationPipe } from '@nestjs/common';
import request from 'supertest';
import { AppModule } from './../src/app.module';
import { ResponseInterceptor } from './../src/common/interceptors/response.interceptor';
import { HttpExceptionFilter } from './../src/common/filters/http-exception.filter';
import { User } from './../src/entities/user.entity';

describe('AuthModule & Response Format (e2e)', () => {
  let app: INestApplication;

  beforeAll(async () => {
    const moduleFixture: TestingModule = await Test.createTestingModule({
      imports: [AppModule],
    }).compile();

    app = moduleFixture.createNestApplication();
    app.setGlobalPrefix('api');
    app.useGlobalPipes(
      new ValidationPipe({
        whitelist: true,
        transform: true,
      }),
    );
    app.useGlobalInterceptors(new ResponseInterceptor());
    app.useGlobalFilters(new HttpExceptionFilter());

    await app.init();
  });

  afterAll(async () => {
    await app.close();
  });

  const testUser = {
    name: 'Elvin Mammadov',
    email: `elvin_${Date.now()}@example.com`,
    password: 'Password123!',
  };

  let verificationToken: string;
  let accessToken: string;
  let resetToken: string;

  it('1. Validation Error returns standard error response structure', async () => {
    const res = await request(app.getHttpServer())
      .post('/api/auth/register')
      .send({ email: 'not-an-email', password: '123' })
      .expect(400);

    expect(res.body).toHaveProperty('success', false);
    expect(res.body).toHaveProperty('error', true);
    expect(res.body).toHaveProperty('statusCode', 400);
    expect(res.body).toHaveProperty('data', null);
    expect(res.body).toHaveProperty('message');
  });

  it('2. Register returns standard success response and saves user', async () => {
    const res = await request(app.getHttpServer())
      .post('/api/auth/register')
      .send(testUser)
      .expect(201);

    expect(res.body).toHaveProperty('success', true);
    expect(res.body).toHaveProperty('error', false);
    expect(res.body).toHaveProperty('statusCode', 201);
    expect(res.body.data).toHaveProperty('user');
    expect(res.body.data.user.email).toBe(testUser.email.toLowerCase());
    expect(res.body.data.user).not.toHaveProperty('password');

    // Retrieve the token from db for testing verify-email
    const userInDb = await User.findOne({ where: { email: testUser.email.toLowerCase() } });
    expect(userInDb).toBeDefined();
    expect(userInDb?.email_verification_token).toBeDefined();
    verificationToken = userInDb!.email_verification_token!;
  });

  it('3. Verify Email using token', async () => {
    const res = await request(app.getHttpServer())
      .post('/api/auth/verify-email')
      .send({ token: verificationToken })
      .expect(200);

    expect(res.body).toHaveProperty('success', true);
    expect(res.body).toHaveProperty('error', false);
    expect(res.body.message).toContain('uğurla təsdiqləndi');

    const userInDb = await User.findOne({ where: { email: testUser.email.toLowerCase() } });
    expect(userInDb?.email_verified_at).not.toBeNull();
    expect(userInDb?.email_verification_token).toBeNull();
  });

  it('4. Login returns JWT access_token', async () => {
    const res = await request(app.getHttpServer())
      .post('/api/auth/login')
      .send({
        email: testUser.email,
        password: testUser.password,
      })
      .expect(200);

    expect(res.body).toHaveProperty('success', true);
    expect(res.body.data).toHaveProperty('access_token');
    expect(res.body.data.user.email).toBe(testUser.email.toLowerCase());
    accessToken = res.body.data.access_token;
  });

  it('5. Get current authenticated user profile (/api/auth/me)', async () => {
    const res = await request(app.getHttpServer())
      .get('/api/auth/me')
      .set('Authorization', `Bearer ${accessToken}`)
      .expect(200);

    expect(res.body).toHaveProperty('success', true);
    expect(res.body.data).toHaveProperty('user');
    expect(res.body.data.user.email).toBe(testUser.email.toLowerCase());
  });

  it('6. Forgot Password generates reset token', async () => {
    const res = await request(app.getHttpServer())
      .post('/api/auth/forgot-password')
      .send({ email: testUser.email })
      .expect(200);

    expect(res.body).toHaveProperty('success', true);
    expect(res.body.message).toContain('Şifrə yeniləmə linki');

    const userInDb = await User.findOne({ where: { email: testUser.email.toLowerCase() } });
    expect(userInDb?.password_reset_token).toBeDefined();
    resetToken = userInDb!.password_reset_token!;
  });

  it('7. Reset Password with token updates password', async () => {
    const newPassword = 'BrandNewPassword456!';
    const res = await request(app.getHttpServer())
      .post('/api/auth/reset-password')
      .send({
        token: resetToken,
        password: newPassword,
      })
      .expect(200);

    expect(res.body).toHaveProperty('success', true);

    // Old password should now fail
    await request(app.getHttpServer())
      .post('/api/auth/login')
      .send({
        email: testUser.email,
        password: testUser.password,
      })
      .expect(401);

    // New password should succeed
    const loginRes = await request(app.getHttpServer())
      .post('/api/auth/login')
      .send({
        email: testUser.email,
        password: newPassword,
      })
      .expect(200);

    expect(loginRes.body.data).toHaveProperty('access_token');
    const currentToken = loginRes.body.data.access_token;

    // 8. Logout blacklists token
    const logoutRes = await request(app.getHttpServer())
      .post('/api/auth/logout')
      .set('Authorization', `Bearer ${currentToken}`)
      .expect(200);

    expect(logoutRes.body).toHaveProperty('success', true);
    expect(logoutRes.body.message).toContain('Uğurla çıxış edildi');

    // 9. Blacklisted token cannot access protected endpoints
    const rejectedRes = await request(app.getHttpServer())
      .get('/api/auth/me')
      .set('Authorization', `Bearer ${currentToken}`)
      .expect(401);

    expect(rejectedRes.body).toHaveProperty('success', false);
    expect(rejectedRes.body.message).toContain('qara siyahıdadır');
  });
});
