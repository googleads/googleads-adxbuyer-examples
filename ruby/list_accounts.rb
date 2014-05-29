#!/usr/bin/env ruby
# Encoding: utf-8
#
# Author:: api.msaniscalchi@gmail.com (Mark Saniscalchi)
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
# Retrieves a list of your Accounts.
#
# Accounts.list

require_relative 'util'

def main(ad_exchange_buyer, params)
  request = ad_exchange_buyer.accounts.list(params)
  response = request.execute()

  if response.success?
    puts "Request successful! Response:\n%s" % [response.body]
  else
    puts "Request failed! Error message:\n%s" % [response.error_message]
  end
end

if __FILE__ == $0
  begin
    service = get_service()

    params = {
        :maxResults => MAX_PAGE_SIZE
    }

  rescue ArgumentError => e
    puts 'Unable to create service: %s' % e.message
    puts 'Did you specify the key file in util.rb?'
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you set the correct Service Account Email in util.rb?'
    exit
  end

  main(service, params)
end
