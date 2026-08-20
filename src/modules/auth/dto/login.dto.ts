import { IsEmail, IsNotEmpty, IsString } from 'class-validator';

export class LoginDto {
  @IsEmail({}, { message: 'Düzgün email ünvanı daxil edin' })
  @IsNotEmpty({ message: 'Email mütləq daxil edilməlidir' })
  email: string;

  @IsString({ message: 'Şifrə mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Şifrə mütləq daxil edilməlidir' })
  password: string;
}
