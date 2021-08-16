# DB Migration 🔀

By using the Java Persistence API (JPA), classed annotated by @Entity (in the [`model`](src/main/java/ai/elimu/model) package) get mapped automatically to database tables/columns: [`src/main/resources/META-INF/jpa-persistence.xml`](https://github.com/elimu-ai/webapp/blob/master/src/main/resources/META-INF/jpa-persistence.xml)

However, when _deleting, modifying or renaming_ an entity class/attribute, we have to manually update the underlying database structure.

## Migration Scripts

Every time the webapp completes initialization, the [`DbMigrationHelper`](src/main/java/ai/elimu/util/db/DbMigrationHelper.java) checks if there are any database migration scripts at [`src/main/resources/db/migration`](https://github.com/elimu-ai/webapp/tree/master/src/main/resources/db/migration) matching the current webapp version. You can find the version in [`pom.xml`](pom.xml#L7).

## How to Add a New Migration Script

Follow these steps:

  1. Lookup the current webapp version in [`pom.xml`](pom.xml#L7)
  2. Add a new file to [`src/main/resources/db/migration`](https://github.com/elimu-ai/webapp/tree/master/src/main/resources/db/migration). E.g. `2003004.sql` for version `2.3.4`.
  3. Add the SQL script that will be executed on the `TEST`/`PROD` server.

## Sample

Let's assume that you want to delete the `text` property from the `Word` entity. It would look like this:
```
ALTER TABLE `Word` DROP COLUMN `text`;
```

For an example of a previous database migration script, see https://github.com/elimu-ai/webapp/commit/9908537ced3b6d64849f7f9967399921dba3d3fc

## Caveats 😅

Note that DB migration performed automatically by the ORM provider (Hibernate), e.g. when adding a new property to an @Entity, is executed _before_ our custom migration scripts.
