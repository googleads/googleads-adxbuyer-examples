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
using Google.Apis.AdExchangeBuyerII.v2beta1;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;

namespace Google.Apis.AdExchangeBuyer.Examples
{
    internal class Program
    {
        /// <summary>
        /// A map to hold the code examples available to be executed.
        /// </summary>
        private static Dictionary<string, ExampleBase> examples
            = new Dictionary<string, ExampleBase>();

        /// <summary>
        /// Static constructor to initialize the examples map.
        /// </summary>
        static Program()
        {
            Type[] types = Assembly.GetExecutingAssembly().GetTypes();

            foreach (Type type in types)
            {
                if (type.IsSubclassOf(typeof(ExampleBase)))
                {
                    ExampleBase example = (ExampleBase)Activator.CreateInstance(type);
                    String exampleName = type.FullName.Replace(
                        typeof(Program).Namespace + ".", "");
                    examples.Add(exampleName, example);
                }
            }
        }

        /// <summary>
        /// The main method.
        /// </summary>
        /// <param name="args">Command line arguments - see ShowUsage for options</param>
        private static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                ShowUsage();
                return;
            }

            Console.WriteLine("AdExchange Buyer DotNet API Sample");
            Console.WriteLine("====================");

            // Create a new service instance for v1 and v2 of AdExchangeBuyer.
            // Note: This is where security configuration takes place and will
            // need to be configured before the code will work!
            AdExchangeBuyerService v1Service = Utilities.GetV1Service();
            AdExchangeBuyerIIService v2Service = Utilities.GetV2Service();

            // If --all is passed run all the examples
            string[] examplesToRun = (args[0].ToLower() == "--all")
                ? examples.Keys.ToArray() : args;

            foreach (string exampleName in examplesToRun)
            {
                if (examples.ContainsKey(exampleName))
                {
                    ExampleBase example = examples[exampleName];
                    Console.WriteLine(example.Description);

                    if(example.getClientType() == ExampleBase.ClientType.ADEXCHANGEBUYER)
                    {
                        example.Run(v1Service);
                    }
                    else if(example.getClientType() == ExampleBase.ClientType.ADEXCHANGEBUYERII)
                    {
                        example.Run(v2Service);
                    }
                }
                else
                {
                    Console.WriteLine("Unrecognised argument " + exampleName);
                    ShowUsage();
                    break;
                }
            }

            Console.WriteLine("Press any key to continue...");
            Console.ReadKey();
        }

        /// <summary>
        /// Prints program usage message.
        /// </summary>
        private static void ShowUsage()
        {
            string exeName = Path.GetFileName(Assembly.GetExecutingAssembly().Location);
            Console.WriteLine("Runs AdxBuyer API code examples");
            Console.WriteLine("Usage : {0} [flags]\n", exeName);
            Console.WriteLine("Available flags\n");
            Console.WriteLine("--all\t\t : Run all code examples.", exeName);
            Console.WriteLine("name1 [name2 ...]:" +
                "Run specific code examples. Example name can be one of the following:\n", 
                exeName);

            foreach (KeyValuePair<string, ExampleBase> pair in examples)
            {
                Console.WriteLine("{0} : {1}", pair.Key, pair.Value.Description);
            }

            Console.WriteLine("Press [Enter] to continue");
            Console.ReadLine();
        }
    }
}