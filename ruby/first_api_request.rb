#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2014, Google Inc. All Rights Reserved.
#
# License:: Licensed under the Apache License, Version 2.0 (the "License");
#           you may not use this file except in compliance with the License.
#           You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#           Unless required by applicable law or agreed to in writing, software
#           distributed under the License is distributed on an "AS IS" BASIS,
#           WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
#           implied.
#           See the License for the specific language governing permissions and
#           limitations under the License.
#
# Sample application that authenticates and makes an API request.

require 'google/api_client/service'

API_NAME = 'adexchangebuyer'
API_VERSION = 'v1.3'

# Update these with the values for your Service Account found in the Google
# Developers Console.
SERVICE_ACCOUNT_EMAIL = 'yourapp@developer.gserviceaccount.com'
KEY_FILE = 'path_to_key'  # Path to *.p12 file containing your private key.
KEY_SECRET = 'notasecret'  # Password for the key file.

def main()
  # Create credentials using the Service email and P12 file.
  key = Google::APIClient::KeyUtils.load_from_pkcs12(KEY_FILE, KEY_SECRET)

  auth_options = {
    :token_credential_uri => 'https://accounts.google.com/o/oauth2/token',
    :audience => 'https://accounts.google.com/o/oauth2/token',
    :scope => 'https://www.googleapis.com/auth/adexchange.buyer',
    :issuer => SERVICE_ACCOUNT_EMAIL,
    :signing_key => key
  }

  oauth_credentials = Signet::OAuth2::Client.new(auth_options)

  # Use the credentials to create a client for the API service.
  service_options = {
    :application_name => "Ruby #{API_NAME} samples: #{$0}",
    :application_version => '1.0.0',
    :authorization => oauth_credentials
  }

  ad_exchange_buyer = Google::APIClient::Service.new(API_NAME, API_VERSION,
                                                     service_options)

  ad_exchange_buyer.authorization.fetch_access_token!

  # Call the Accounts resource on the service to retrieve a list of
  # Accounts for the service account.
  request = ad_exchange_buyer.accounts.list()
  response = request.execute()

  if response.success?
    puts "Request successful! Response:\n%s" % [response.body]
  else
    puts "Request failed! Error message:\n%s" % [response.error_message]
  end
end

if __FILE__ == $0
  begin
    main()
  end
end

