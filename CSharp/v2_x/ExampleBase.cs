﻿/* Copyright 2014, Google Inc. All Rights Reserved.
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

using Google.Apis.Services;

namespace Google.Apis.AdExchangeBuyer.Examples
{
    /// <summary>
    /// This abstract class represents a code example.
    /// </summary>
    public abstract class ExampleBase
    {
        public enum ClientType {ADEXCHANGEBUYERII}
        /// <summary>
        /// Returns a description about the code example.
        /// </summary>
        public abstract string Description
        {
            get;
        }

        /// <summary>
        /// Returns the ClientType necessary to run the sample.
        /// </summary>

        public abstract ClientType getClientType();

        public abstract void Run(BaseClientService service);
    }
}
