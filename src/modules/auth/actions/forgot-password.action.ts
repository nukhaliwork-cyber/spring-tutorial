import { Injectable, NotFoundException } from '@nestjs/common';
import * as crypto from 'crypto';
import { User } from '../../../entities/user.entity';
import { ForgotPasswordDto } from '../dto/forgot-password.dto';
import { MailService } from '../../mail/mail.service';

@Injectable()
export class ForgotPasswordAction {
  constructor(private readonly mailService: MailService) {}

  async execute(dto: ForgotPasswordDto): Promise<{ message: string }> {
    const user = await User.findOne({
      where: { email: dto.email.toLowerCase() },
    });

    if (!user) {
      throw new NotFoundException('Bu email ünvanı ilə qeydiyyatdan keçmiş istifadəçi tapılmadı');
    }

    const resetToken = crypto.randomBytes(32).toString('hex');
    const expiresAt = new Date();
    expiresAt.setHours(expiresAt.getHours() + 1); // 1 hour validity

    user.password_reset_token = resetToken;
    user.password_reset_expires_at = expiresAt;
    await user.save();

    await this.mailService.sendPasswordResetEmail(user.email, resetToken, user.name);

    return {
      message: 'Şifrə yeniləmə linki email ünvanınıza göndərildi',
    };
  }
}
