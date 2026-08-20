import { Injectable, UnauthorizedException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import * as bcrypt from 'bcryptjs';
import { User } from '../../../entities/user.entity';
import { LoginDto } from '../dto/login.dto';

@Injectable()
export class LoginAction {
  constructor(private readonly jwtService: JwtService) {}

  async execute(dto: LoginDto): Promise<{ access_token: string; user: Partial<User> }> {
    const user = await User.findOne({
      where: { email: dto.email.toLowerCase() },
    });

    if (!user) {
      throw new UnauthorizedException('Email və ya şifrə yanlışdır');
    }

    const isPasswordValid = await bcrypt.compare(dto.password, user.password);
    if (!isPasswordValid) {
      throw new UnauthorizedException('Email və ya şifrə yanlışdır');
    }

    const payload = {
      sub: user.id,
      email: user.email,
      is_admin: user.is_admin,
    };

    const accessToken = await this.jwtService.signAsync(payload);

    const { password, email_verification_token, password_reset_token, ...safeUser } = user;

    return {
      access_token: accessToken,
      user: safeUser,
    };
  }
}
