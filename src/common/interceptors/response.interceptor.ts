import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { IApiResponse } from '../interfaces/api-response.interface';

@Injectable()
export class ResponseInterceptor<T>
  implements NestInterceptor<T, IApiResponse<T>>
{
  intercept(
    context: ExecutionContext,
    next: CallHandler,
  ): Observable<IApiResponse<T>> {
    const response = context.switchToHttp().getResponse();
    const statusCode = response.statusCode || 200;

    return next.handle().pipe(
      map((result) => {
        // If result already formatted as an IApiResponse object
        if (
          result &&
          typeof result === 'object' &&
          'success' in result &&
          'error' in result &&
          'statusCode' in result
        ) {
          return result;
        }

        // If returned object has custom message and data wrapper
        if (
          result &&
          typeof result === 'object' &&
          ('data' in result || 'message' in result)
        ) {
          const { message, data, ...rest } = result;
          return {
            success: true,
            error: false,
            statusCode,
            message: message ?? 'Operation successful',
            data: data !== undefined ? data : Object.keys(rest).length > 0 ? rest : null,
          };
        }

        return {
          success: true,
          error: false,
          statusCode,
          message: 'Operation successful',
          data: result !== undefined ? result : null,
        };
      }),
    );
  }
}
