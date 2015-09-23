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

using Google.Apis.AdExchangeBuyer.v1_4;
using Google.Apis.Auth.OAuth2;
using Google.Apis.Auth.OAuth2.Flows;
using Google.Apis.Http;
using Google.Apis.Util.Store;

using System.IO;
using System.Security.Cryptography.X509Certificates;
using System.Threading;

namespace Google.Apis.AdExchangeBuyer.Examples
{
    public class Utilities
    {
        /// <summary>
        /// Create a new API Service. Note the call to ServiceAccount - this is
        /// where security configuration takes place and will need to be
        /// configured before the code will work!
        /// </summary>
        /// <returns>A new API Service</returns>
        public static AdExchangeBuyerService GetService()
        {
            return new AdExchangeBuyerService(
                new Google.Apis.Services.BaseClientService.Initializer
                {
                    HttpClientInitializer = ServiceAccount(),
                    ApplicationName = "AdExchange Buyer DotNet Sample",
                }
            );
        }

        /// <summary>
        /// Uses a .p12 KeyFile to authenticate a service account and return
        /// credentials for accessing the API
        /// </summary>
        /// <returns>Authentication object for API Requests</returns>
        public static IConfigurableHttpClientInitializer ServiceAccount()
        {
            var certificate = new X509Certificate2(ExamplesConfig.ServiceKeyFilePath,
                ExamplesConfig.ServiceKeyFilePassword, X509KeyStorageFlags.Exportable);

            return new ServiceAccountCredential(
               new ServiceAccountCredential.Initializer(ExamplesConfig.ServiceAccountEmail)
               {
                   Scopes = new[] { AdExchangeBuyerService.Scope.AdexchangeBuyer }
               }.FromCertificate(certificate));
        }

        /// <summary>
        /// Extracts info from a JSON file and prompts the user to login and
        /// authorize the application. Returns credentials for accessing the API
        /// Note: After the first authentication a RefreshToken is cached and
        ///       used for subsequent calls via FileDataStore("adxbuyer")
        /// </summary>
        /// <returns>Authentication object for API Requests</returns>
        public static IConfigurableHttpClientInitializer Prompt()
        {
            using (var stream = new FileStream(ExamplesConfig.ClientSecretLocation,
                FileMode.Open, FileAccess.Read))
            {
                return GoogleWebAuthorizationBroker.AuthorizeAsync(
                    GoogleClientSecrets.Load(stream).Secrets,
                    new[] { AdExchangeBuyerService.Scope.AdexchangeBuyer },
                    "user",
                    CancellationToken.None,
                    new FileDataStore(ExamplesConfig.FileDataStore)).Result;
            }
        }

        /// <summary>
        /// Uses hard coded info to authenticate and return credentials
        /// Note: All of the parameters required for this method can be
        ///       retrieved from the JSON File and the cache file from the
        ///       Prompt() method above
        /// </summary>
        /// <returns>Authentication object for API Requests</returns>
        public static IConfigurableHttpClientInitializer RefreshToken()
        {
            var token = new Google.Apis.Auth.OAuth2.Responses.TokenResponse
            {
                RefreshToken = ExamplesConfig.RefreshToken
            };
            return new UserCredential(new GoogleAuthorizationCodeFlow(
                new GoogleAuthorizationCodeFlow.Initializer
                {
                    ClientSecrets = new ClientSecrets
                    {
                        ClientId = ExamplesConfig.ClientId,
                        ClientSecret = ExamplesConfig.ClientSecret
                    },
                }), "user", token);
        }
    }
}