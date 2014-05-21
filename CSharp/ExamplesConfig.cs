/* Copyright 2014, Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

using System;

namespace Google.Apis.AdExchangeBuyer.Examples
{
    static public class ExamplesConfig
    {
        // Only required when using Service Account for authentication. This is
        // the default auth method.

        // See the README.md for details of these fields. 
        // Retrieved from https://console.developers.google.com
        public static String ServiceAccountEmail = "SERVICE ACCOUNT EMAIL HERE";

        public static String ServiceKeyFilePath = "PATH TO P12 KEYFILE HERE-INCLUDE FILENAME";
        public static String ServiceKeyFilePassword = "notasecret";

        // Only required when using Prompt for authentication. See
        // https://devsite.googleplex.com/ad-exchange/buyer-rest/auth-guide for
        // Auth details.

        // Full path to client secrets file.
        public static String ClientSecretLocation = "PATH TO CLIENT SECRETS HERE-INCLUDE FILENAME";

        // Name of folder to cache credentials when using Tokens for Auth.
        public static String FileDataStore = "adxbuyer";

        // Only required when using RefreshToken for authentication. See
        // https://devsite.googleplex.com/ad-exchange/buyer-rest/auth-guide for
        // Auth details.

        // Generated Refresh Token.
        public static String RefreshToken = "REFRESH TOKEN HERE";

        // Both retrieved from https://console.developers.google.com
        public static String ClientId = "CLIENT ID HERE";

        public static String ClientSecret = "CLIENT SECRET HERE";
    }
}