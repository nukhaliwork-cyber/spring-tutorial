import { Injectable, BadRequestException, NotFoundException } from '@nestjs/common';
import * as crypto from 'crypto';
import { User } from '../../../entities/user.entity';
import { ResendVerificationDto } from '../dto/resend-verification.dto';
import { MailService } from '../../mail/mail.service';

@Injectable()
export class ResendVerificationAction {
  constructor(private readonly mailService: MailService) {}

  async execute(dto: ResendVerificationDto): Promise<{ message: string }> {
    const user = await User.findOne({
      where: { email: dto.email.toLowerCase() },
    });

    if (!user) {
      throw new NotFoundException('Bu email ilə istifadəçi tapılmadı');
    }

    if (user.email_verified_at) {
      throw new BadRequestException('Bu email ünvanı artıq təsdiqlənib');
    }

    const verificationToken = crypto.randomBytes(32).toString('hex');
    user.email_verification_token = verificationToken;
    await user.save();

    await this.mailService.sendVerificationEmail(user.email, verificationToken, user.name);

    return {
      message: 'Təsdiq emaili yenidən göndərildi',
    };
  }
}
