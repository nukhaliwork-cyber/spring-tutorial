import { IsNotEmpty, IsString } from 'class-validator';

export class VerifyEmailDto {
  @IsString({ message: 'Token mətn tipində olmalıdır' })
  @IsNotEmpty({ message: 'Token mütləq daxil edilməlidir' })
  token: string;
}
