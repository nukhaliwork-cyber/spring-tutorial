import { Injectable, ConflictException } from '@nestjs/common';
import * as bcrypt from 'bcryptjs';
import * as crypto from 'crypto';
import { User } from '../../../entities/user.entity';
import { RegisterDto } from '../dto/register.dto';
import { MailService } from '../../mail/mail.service';

@Injectable()
export class RegisterAction {
  constructor(private readonly mailService: MailService) {}

  async execute(dto: RegisterDto): Promise<{ user: Partial<User>; message: string }> {
    const existingUser = await User.findOne({ where: { email: dto.email.toLowerCase() } });
    if (existingUser) {
      throw new ConflictException('Bu email ünvanı ilə artıq qeydiyyatdan keçilib');
    }

    const hashedPassword = await bcrypt.hash(dto.password, 10);
    const verificationToken = crypto.randomBytes(32).toString('hex');

    const user = new User();
    user.name = dto.name;
    user.email = dto.email.toLowerCase();
    user.password = hashedPassword;
    user.is_admin = false;
    user.email_verification_token = verificationToken;
    user.email_verified_at = null;

    await user.save();

    await this.mailService.sendVerificationEmail(user.email, verificationToken, user.name);

    const { password, email_verification_token, password_reset_token, ...safeUser } = user;

    return {
      user: safeUser,
      message: 'Qeydiyyat uğurla tamamlandı. Zəhmət olmasa email ünvanınızı təsdiqləyin.',
    };
  }
}
