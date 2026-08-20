import { IsNotEmpty, IsString, MinLength } from 'class-validator';

export class ResetPasswordDto {
  @IsString({ message: 'Token mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Token mütləq daxil edilməlidir' })
  token: string;

  @IsString({ message: 'Şifrə mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Şifrə mütləq daxil edilməlidir' })
  @MinLength(6, { message: 'Yeni şifrə ən azı 6 simvol olmalıdır' })
  password: string;
}
