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
using Google.Apis.Services;

using System;
using System.Security.Cryptography.X509Certificates;

namespace Google.Apis.AdExchangeBuyer.Examples.v1_x
{
    /// <summary>
    /// Self contained sample to return a list of Accounts
    /// Primarily used by the Getting Started guide 
    /// https://developers.google.com/ad-exchange/buyer-rest/getting_started
    /// </summary>
    internal class FirstApiRequest
    {
        private static void Main(string[] args)
        {
            // See the README.md for details of these fields. 
            // Retrieved from https://console.developers.google.com
            String ServiceAccountEmail = "SERVICE ACCOUNT EMAIL HERE";
            String ServiceKeyFilePath = "PATH TO P12 KEYFILE HERE-INCLUDE FILENAME";
            String ServiceKeyFilePassword = "notasecret";

            // Create a certificate object using the key file.
            X509Certificate2 certificate = new X509Certificate2(ServiceKeyFilePath,
                ServiceKeyFilePassword, X509KeyStorageFlags.Exportable);

            // Use the certificate to create credentials.
            ServiceAccountCredential oAuth2Credentials = new ServiceAccountCredential(
                new ServiceAccountCredential.Initializer(ServiceAccountEmail)
                {
                    Scopes = new[] { AdExchangeBuyerService.Scope.AdexchangeBuyer }
                }.FromCertificate(certificate));

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
