// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  apiEndpointUrl: 'http://localhost:4200/api',
  pagingOptions : { start : 1, size  :10, sortOrder : "asc", sort : "id"},
  appTitle : "Employee Data Management Tool",
  appVersion : '1.1.0',
  copyrightYear : '2019'
};
