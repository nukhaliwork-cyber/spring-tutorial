import { Injectable } from '@nestjs/common';
import { RegisterDto } from './dto/register.dto';
import { LoginDto } from './dto/login.dto';
import { VerifyEmailDto } from './dto/verify-email.dto';
import { ResendVerificationDto } from './dto/resend-verification.dto';
import { ForgotPasswordDto } from './dto/forgot-password.dto';
import { ResetPasswordDto } from './dto/reset-password.dto';
import { User } from '../../entities/user.entity';
import {
  RegisterAction,
  LoginAction,
  LogoutAction,
  VerifyEmailAction,
  ResendVerificationAction,
  ForgotPasswordAction,
  ResetPasswordAction,
} from './actions';

@Injectable()
export class AuthService {
  constructor(
    private readonly registerAction: RegisterAction,
    private readonly loginAction: LoginAction,
    private readonly logoutAction: LogoutAction,
    private readonly verifyEmailAction: VerifyEmailAction,
    private readonly resendVerificationAction: ResendVerificationAction,
    private readonly forgotPasswordAction: ForgotPasswordAction,
    private readonly resetPasswordAction: ResetPasswordAction,
  ) {}

  async register(dto: RegisterDto) {
    return this.registerAction.execute(dto);
  }

  async login(dto: LoginDto) {
    return this.loginAction.execute(dto);
  }

  async logout(token: string, user?: User) {
    return this.logoutAction.execute(token, user);
  }

  async verifyEmail(dto: VerifyEmailDto) {
    return this.verifyEmailAction.execute(dto);
  }

  async resendVerification(dto: ResendVerificationDto) {
    return this.resendVerificationAction.execute(dto);
  }

  async forgotPassword(dto: ForgotPasswordDto) {
    return this.forgotPasswordAction.execute(dto);
  }

  async resetPassword(dto: ResetPasswordDto) {
    return this.resetPasswordAction.execute(dto);
  }
}
