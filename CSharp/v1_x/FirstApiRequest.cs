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
using Google.Apis.AdExchangeBuyer.v1_4.Data;
using Google.Apis.Auth.OAuth2;
using Google.Apis.Json;
using Google.Apis.Services;

using System;

namespace Google.Apis.AdExchangeBuyer.Examples.v1_x
{

    /// <summary>
    /// Self contained sample to return a list of Accounts.
    /// Primarily used by the Getting Started guide:
    /// https://developers.google.com/authorized-buyers/apis/getting_started
    /// </summary>
    internal class FirstApiRequest
    {

        private static void Main(string[] args)
        {
            // See the README.md for details of these fields.
            // Retrieved from https://console.developers.google.com
            String ServiceKeyFilePath = "PATH TO JSON KEY FILE HERE";

            // Retrieve credential parameters from the key JSON file.
            var credentialParameters = NewtonsoftJsonSerializer.Instance
                .Deserialize<JsonCredentialParameters>(System.IO.File
                .ReadAllText(ServiceKeyFilePath));

            // Create the credentials.
            ServiceAccountCredential oAuth2Credentials = new ServiceAccountCredential(
                new ServiceAccountCredential.Initializer(
                    credentialParameters.ClientEmail)
                {
                    Scopes = new[] { AdExchangeBuyerService.Scope.AdexchangeBuyer }
                }.FromPrivateKey(credentialParameters.PrivateKey));

            // Use the credentials to create a client for the API service.
            AdExchangeBuyerService buyerService = new AdExchangeBuyerService(
                new BaseClientService.Initializer
                {
                    HttpClientInitializer = oAuth2Credentials,
                    ApplicationName = "FirstAPICall"
                });

            // Call the Accounts resource on the service to retrieve a list of
            // Accounts for the service account
            AccountsList allAccounts = buyerService.Accounts.List().Execute();

            foreach (Account account in allAccounts.Items)
            {
                Console.WriteLine("Account id: {0}", account.Id);
            }

            Console.ReadLine();
        }
    }
}

