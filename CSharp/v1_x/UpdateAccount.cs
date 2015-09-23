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

using System;

namespace Google.Apis.AdExchangeBuyer.Examples.v1_x
{
    /// <summary>
    /// Updates an existing account using patch semantics. You can find out more
    /// here https://developers.google.com/ad-exchange/buyer-rest/v1.3/accounts/patch
    /// </summary>
    public class UpdateAccount : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerService service = Utilities.GetService();
            ExampleBase example = new UpdateAccount();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get
            {
                return "This code example updates the CookieMatchingUrl on a"
                    + " specified account.";
            }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(AdExchangeBuyerService service)
        {
            int accountId = int.Parse("INSERT ACCOUNT ID HERE");

            Account account = new Account
            {
                Id = accountId,
                CookieMatchingUrl = "UPDATED COOKIE MATCHING URL HERE",
            };

            Account responseAccount = service.Accounts.Patch(account, accountId).Execute();

            Console.WriteLine("Updated account");
            Console.WriteLine("Account id: {0}", responseAccount.Id);
            Console.WriteLine("Cookie Matching Url: {0}", responseAccount.CookieMatchingUrl);
        }
    }
}