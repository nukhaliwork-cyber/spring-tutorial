import { MigrationInterface, QueryRunner, Table, TableIndex } from 'typeorm';

export class CreateJwtBlacklistsTable1700000000004 implements MigrationInterface {
  public async up(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.createTable(
      new Table({
        name: 'jwt_blacklists',
        columns: [
          {
            name: 'id',
            type: 'integer',
            isPrimary: true,
            isGenerated: true,
            generationStrategy: 'increment',
          },
          {
            name: 'token',
            type: 'text',
            isNullable: false,
          },
          {
            name: 'user_id',
            type: 'integer',
            isNullable: true,
          },
          {
            name: 'expires_at',
            type: 'datetime',
            isNullable: false,
          },
          {
            name: 'created_at',
            type: 'datetime',
            default: "datetime('now')",
            isNullable: false,
          },
        ],
      }),
      true,
    );

    await queryRunner.createIndex(
      'jwt_blacklists',
      new TableIndex({
        name: 'IDX_jwt_blacklists_token',
        columnNames: ['token'],
      }),
    );
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.dropTable('jwt_blacklists');
  }
}
