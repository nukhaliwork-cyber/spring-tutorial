import { IsEmail, IsNotEmpty } from 'class-validator';

export class ResendVerificationDto {
  @IsEmail({}, { message: 'Düzgün email ünvanı daxil edin' })
  @IsNotEmpty({ message: 'Email mütləq daxil edilməlidir' })
  email: string;
}
