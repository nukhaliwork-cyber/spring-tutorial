import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable, UnauthorizedException } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import type { Request } from 'express';
import { User } from '../../../entities/user.entity';
import { BlacklistedToken } from '../../../entities/blacklisted-token.entity';

export interface JwtPayload {
  sub: number;
  email: string;
  is_admin: boolean;
}

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(configService: ConfigService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: configService.get<string>(
        'JWT_SECRET',
        'super_secret_jwt_key_change_in_production_2026',
      ),
      passReqToCallback: true,
    });
  }

  async validate(req: Request, payload: JwtPayload): Promise<User> {
    const rawToken = ExtractJwt.fromAuthHeaderAsBearerToken()(req);

    if (rawToken) {
      const isBlacklisted = await BlacklistedToken.findOne({
        where: { token: rawToken },
      });
      if (isBlacklisted) {
        throw new UnauthorizedException('Bu sessiya artıq bitib (token qara siyahıdadır)');
      }
    }

    const user = await User.findOne({ where: { id: payload.sub } });
    if (!user) {
      throw new UnauthorizedException('İstifadəçi tapılmadı və ya etibarsız token');
    }
    return user;
  }
}
