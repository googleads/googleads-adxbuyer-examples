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
    /// Retrieves the authenticated user's list of accounts.
    /// </summary>
    public class ListAccounts : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerService service = Utilities.GetService();
            ExampleBase example = new ListAccounts();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example lists all accounts."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(AdExchangeBuyerService service)
        {
            AccountsList allAccounts = service.Accounts.List().Execute();
            foreach (Account account in allAccounts.Items)
            {
                Console.WriteLine("Account id: {0}", account.Id);
                Console.WriteLine("\tMax. total Qps: {0}", account.MaximumTotalQps);
                Console.WriteLine("\tCookie matching Nid: {0}", account.CookieMatchingNid);
                Console.WriteLine("\tCookie Matching Url: {0}", account.CookieMatchingUrl);
            }
        }
    }
}