import { Injectable, BadRequestException } from '@nestjs/common';
import * as bcrypt from 'bcryptjs';
import { MoreThan } from 'typeorm';
import { User } from '../../../entities/user.entity';
import { ResetPasswordDto } from '../dto/reset-password.dto';

@Injectable()
export class ResetPasswordAction {
  async execute(dto: ResetPasswordDto): Promise<{ message: string }> {
    const user = await User.findOne({
      where: {
        password_reset_token: dto.token,
        password_reset_expires_at: MoreThan(new Date()),
      },
    });

    if (!user) {
      throw new BadRequestException('Yanlış və ya vaxtı bitmiş şifrə yeniləmə tokeni');
    }

    const hashedPassword = await bcrypt.hash(dto.password, 10);
    user.password = hashedPassword;
    user.password_reset_token = null;
    user.password_reset_expires_at = null;
    await user.save();

    return {
      message: 'Şifrəniz uğurla yeniləndi. Yeni şifrənizlə daxil ola bilərsiniz.',
    };
  }
}
