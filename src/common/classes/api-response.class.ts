import { IApiResponse } from '../interfaces/api-response.interface';

export class ApiResponseFormatter {
  static success<T>(
    data: T | null = null,
    message: string = 'Operation successful',
    statusCode: number = 200,
  ): IApiResponse<T> {
    return {
      success: true,
      error: false,
      statusCode,
      message,
      data,
    };
  }

  static error(
    message: string = 'Operation failed',
    statusCode: number = 400,
    errors: any = null,
  ): IApiResponse<null> {
    return {
      success: false,
      error: true,
      statusCode,
      message,
      data: null,
      ...(errors ? { errors } : {}),
    };
  }
}
