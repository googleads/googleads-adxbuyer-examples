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
    /// Inserts a new pretargeting config.
    /// </summary>
    public class InsertPretargetingConfig : ExampleBase
    {
        /// <summary>
        /// Main method, to run this code example as a standalone application.
        /// </summary>
        /// <param name="args">The command line arguments</param>
        public static void Main(string[] args)
        {
            AdExchangeBuyerService service = Utilities.GetService();
            ExampleBase example = new InsertPretargetingConfig();
            Console.WriteLine(example.Description);
            example.Run(service);
        }

        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public override string Description
        {
            get { return "This code example inserts a new pretargeting config."; }
        }

        /// <summary>
        /// Runs the code example.
        /// </summary>
        /// <param name="service">An authenticated AdExchangeBuyerService</param>
        public override void Run(AdExchangeBuyerService service)
        {
            long accountId = long.Parse("INSERT ACCOUNT ID HERE");
            string configName = "INSERT CONFIG NAME HERE";
            PretargetingConfig.DimensionsData dimensions = new PretargetingConfig.DimensionsData
            {
                Width = int.Parse("INSERT WIDTH HERE"),
                Height = int.Parse("INSERT HEIGHT HERE")
            };
            bool active = bool.Parse("INSERT TRUE OR FALSE HERE");

            PretargetingConfig config = new PretargetingConfig
            {
                ConfigName = configName,
                CreativeType = new[] { "PRETARGETING_CREATIVE_TYPE_HTML" },
                Dimensions = new[] { dimensions },
                IsActive = active
            };

            PretargetingConfig responseConfig = service.PretargetingConfig.
                Insert(config, accountId).Execute();

            Console.WriteLine("Inserted new pretargeting config:");
            Console.WriteLine("Config Name: {0}", responseConfig.ConfigName);
            Console.WriteLine("Config Id: {0}", responseConfig.ConfigId);
            Console.WriteLine("Is active: {0}", responseConfig.IsActive);
            Console.WriteLine("Creative Type: {0}", responseConfig.CreativeType);
        }
    }
}