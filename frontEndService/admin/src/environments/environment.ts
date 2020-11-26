// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const ip = '127.0.0.1';
const port = '8080';

export const environment = {
    production: false,
    APIEndPoint: `http://${ip}:${port}`,
    googleApi: 'https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=763843322066-hckctr2gs228c0t4pnuuqcc4qku54934.apps.googleusercontent.com&redirect_uri=http://localhost:8288/Callback&response_type=code&scope=https://www.googleapis.com/auth/spreadsheets',
    requestAuth: 'http://localhost:8080/provider/auth/rules',
    getRules: 'http://localhost:8080/provider/rules'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
