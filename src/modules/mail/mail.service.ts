import { Injectable, Logger } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class MailService {
  private readonly logger = new Logger(MailService.name);

  constructor(private readonly configService: ConfigService) {}

  async sendVerificationEmail(email: string, token: string, name: string): Promise<void> {
    const appUrl = this.configService.get<string>('APP_URL', 'http://localhost:3000');
    const verificationUrl = `${appUrl}/api/auth/verify-email?token=${token}`;

    this.logger.log('================ [EMAIL VERIFICATION] ================');
    this.logger.log(`To: ${name} <${email}>`);
    this.logger.log(`Subject: E-mail ünvanınızı təsdiqləyin`);
    this.logger.log(`Verification Token: ${token}`);
    this.logger.log(`Link: ${verificationUrl}`);
    this.logger.log('======================================================');
  }

  async sendPasswordResetEmail(email: string, token: string, name: string): Promise<void> {
    const appUrl = this.configService.get<string>('APP_URL', 'http://localhost:3000');
    const resetUrl = `${appUrl}/api/auth/reset-password?token=${token}`;

    this.logger.log('================ [PASSWORD RESET] ================');
    this.logger.log(`To: ${name} <${email}>`);
    this.logger.log(`Subject: Şifrənizi yeniləyin`);
    this.logger.log(`Reset Token: ${token}`);
    this.logger.log(`Link: ${resetUrl}`);
    this.logger.log('==================================================');
  }
}
