import { Injectable, BadRequestException } from '@nestjs/common';
import { JwtService } from '@nestjs/jwt';
import { BlacklistedToken } from '../../../entities/blacklisted-token.entity';
import { User } from '../../../entities/user.entity';

@Injectable()
export class LogoutAction {
  constructor(private readonly jwtService: JwtService) {}

  async execute(token: string, user?: User): Promise<{ message: string }> {
    if (!token) {
      throw new BadRequestException('Token tələb olunur');
    }

    try {
      const decoded: any = this.jwtService.decode(token);
      const expiresAt = decoded?.exp
        ? new Date(decoded.exp * 1000)
        : new Date(Date.now() + 7 * 24 * 60 * 60 * 1000);

      // Check if already blacklisted
      const exists = await BlacklistedToken.findOne({ where: { token } });
      if (!exists) {
        const blacklisted = new BlacklistedToken();
        blacklisted.token = token;
        blacklisted.user_id = user?.id ?? (decoded?.sub ? Number(decoded.sub) : null);
        blacklisted.expires_at = expiresAt;
        await blacklisted.save();
      }

      return {
        message: 'Uğurla çıxış edildi',
      };
    } catch {
      throw new BadRequestException('Etibarsız token');
    }
  }
}
