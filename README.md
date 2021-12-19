# madhead/read-java-properties

GitHub Action to read values from Java's `.properties` files.

## Usage

Returning a single property is as simple as:

```yaml
- uses: madhead/read-java-properties@latest
  id: version
  with:
    file: gradle.properties
    property: version
    default: 0.0.1

- run: echo ${{ steps.version.outputs.value }} # Project's version from gradle.properties or 0.0.1 if it is not defined there
```

Alternatively, you could return all the values from the `.properties` file by employing the `all` flag:

```yaml
- uses: madhead/read-java-properties@latest
  id: all
  with:
    file: gradle.properties
    all: true

- run: echo ${{ steps.all.outputs.version }} # Project's version from gradle.properties
- run: echo ${{ steps.all.outputs.groupId }} # Project's groupID from gradle.properties
  …
```

To see the list of available versions of this action (`latest` in the example above), navigate to the [Releases & Tags](https://github.com/madhead/read-java-properties/tags) page of this repo.
Whenever a new version is released, corresponding tags are created / updated.
`latest` tag always points to the latest release.
`master` label could also be used, being a synonym to `latest`.
There are also `$major` and `$major.$minor` tags pointing to the latest matching version (i.e. tag `1` always points to the latest `1.x` version, and tag `1.1` — to the latest `1.1.x` version).

To see this action… in action… check its integration test in [`test.yml`](.github/workflows/test.yml).

### ERROR: `JAVA_HOME` is set to an invalid directory

Note, that due to the environment variables "leaking" from the workflow into the Docker container actions, if you workflow defines a `JAVA_HOME` variable, this action would fail.

Try not to set `JAVA_HOME` before running this action until this "leakage" is "fixed" by the GitHub Actions team.

Read more about this issue in [#28](https://github.com/madhead/read-java-properties/issues/28) and check the [minimal reproducible example](https://github.com/madhead/actions-env-leak).
