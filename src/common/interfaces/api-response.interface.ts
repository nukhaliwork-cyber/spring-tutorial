export interface IApiResponse<T = any> {
  success: boolean;
  error: boolean;
  statusCode: number;
  message: string | null;
  data: T | null;
  errors?: any;
}
