# DB Migration 🔀

By using the Java Persistence API (JPA), classed annotated by @Entity (in the [`model`](src/main/java/ai/elimu/model) package) get mapped automatically to database tables/columns: [`src/main/resources/META-INF/jpa-persistence.xml`](https://github.com/elimu-ai/webapp/blob/main/src/main/resources/META-INF/jpa-persistence.xml)

> [!IMPORTANT]
> However, when _deleting, modifying or renaming_ an entity class/attribute, we have to manually update the underlying database structure.

## Migration Scripts

Every time the webapp completes initialization, the [`DbMigrationHelper`](src/main/java/ai/elimu/util/db/DbMigrationHelper.java) checks if there are any database migration scripts at [`src/main/resources/db/migration`](https://github.com/elimu-ai/webapp/tree/main/src/main/resources/db/migration) matching the current webapp version. You can find the version in [`pom.xml`](pom.xml#L7).

## How to Add a New Migration Script

Follow these steps:

  1. Lookup the current webapp version in [`pom.xml`](pom.xml#L7)
  2. Add a new file to [`src/main/resources/db/migration`](https://github.com/elimu-ai/webapp/tree/main/src/main/resources/db/migration). E.g. `2003004.sql` for version `2.3.4`.
  3. Add the SQL script that will be executed on the `TEST`/`PROD` server.

## Sample

Let's assume that you want to delete the `text` property from the `Word` entity. It would look like this:
```
ALTER TABLE `Word` DROP COLUMN `text`;
```

For an example of a previous database migration script, see https://github.com/elimu-ai/webapp/commit/9908537ced3b6d64849f7f9967399921dba3d3fc

## Caveats 😅

> [!WARNING]
> Note that DB migration performed automatically by the ORM provider (Hibernate), e.g. when adding a new property to an `@Entity`, is executed _before_ our custom migration scripts.

---

<p align="center">
  <img src="https://github.com/elimu-ai/webapp/blob/main/src/main/webapp/static/img/logo-text-256x78.png" />
</p>
<p align="center">
  elimu.ai - Free open-source learning software for out-of-school children ✨🚀
</p>
<p align="center">
  <a href="https://elimu.ai">Website 🌐</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#readme">Wiki 📃</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/orgs/elimu-ai/projects?query=is%3Aopen">Projects 👩🏽‍💻</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki/milestones">Milestones 🎯</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#open-source-community">Community 👋🏽</a>
  &nbsp;•&nbsp;
  <a href="https://www.drips.network/app/drip-lists/41305178594442616889778610143373288091511468151140966646158126636698">Support 💜</a>
</p>
