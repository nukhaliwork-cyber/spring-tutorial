import { Injectable, BadRequestException } from '@nestjs/common';
import { User } from '../../../entities/user.entity';
import { VerifyEmailDto } from '../dto/verify-email.dto';

@Injectable()
export class VerifyEmailAction {
  async execute(dto: VerifyEmailDto): Promise<{ message: string; user: Partial<User> }> {
    const user = await User.findOne({
      where: { email_verification_token: dto.token },
    });

    if (!user) {
      throw new BadRequestException('Yanlış və ya vaxtı keçmiş təsdiq tokeni');
    }

    user.email_verified_at = new Date();
    user.email_verification_token = null;
    await user.save();

    const { password, email_verification_token, password_reset_token, ...safeUser } = user;

    return {
      message: 'Email ünvanı uğurla təsdiqləndi',
      user: safeUser,
    };
  }
}
