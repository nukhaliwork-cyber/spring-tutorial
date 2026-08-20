import { IsEmail, IsNotEmpty, IsString, MinLength } from 'class-validator';

export class RegisterDto {
  @IsString({ message: 'Ad mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Ad mütləq daxil edilməlidir' })
  @MinLength(2, { message: 'Ad ən azı 2 simvol olmalıdır' })
  name: string;

  @IsEmail({}, { message: 'Düzgün email ünvanı daxil edin' })
  @IsNotEmpty({ message: 'Email mütləq daxil edilməlidir' })
  email: string;

  @IsString({ message: 'Şifrə mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Şifrə mütləq daxil edilməlidir' })
  @MinLength(6, { message: 'Şifrə ən azı 6 simvol olmalıdır' })
  password: string;
}
