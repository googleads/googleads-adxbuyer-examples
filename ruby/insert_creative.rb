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
# Inserts a Creative with the given values. To get Account IDs, run
# list_accounts.rb.
#
# Tags: Creatives.insert

require_relative 'util'


def main(ad_exchange_buyer, body)
  request = ad_exchange_buyer.creatives.insert()
  request.body = body

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

    body = {
        :accountId => 'INSERT_ACCOUNT_ID_HERE'.to_i,
        :advertiserName => 'INSERT_ADVERTISER_NAME',
        :buyerCreativeId => 'INSERT_BCID',
        :clickThroughUrl => ['INSERT_URL'],
        :HTMLSnippet => 'INSERT_HTML_SNIPPET',
        :height => 'INSERT_CREATIVE_HEIGHT'.to_i,
        :width => 'INSERT_CREATIVE_WIDTH'.to_i
    }

    if body[:accountId] == 0
      raise 'You need to set the accountId!'
    end

    if body[:advertiserName] === 'INSERT_ADVERTISER_NAME'
      raise 'You need to set the advertiserName!'
    end

    if body[:buyerCreativeId] === 'INSERT_BCID'
      raise 'You need to set the buyerCreativeId!'
    end

    if body[:clickThroughUrl][0] === 'INSERT_URL'
      raise 'You need to set the clickThroughUrl!'
    end

    if body[:HTMLSnippet] === 'INSERT_HTML_SNIPPET'
      raise 'You need to set the HTMLSnippet!'
    end

    if body[:height] == 0
      raise 'You need to set the creative\'s height!'
    end

    if body[:width] == 0
      raise 'You need to set the creative\'s width!'
    end

  rescue ArgumentError => e
    puts 'Unable to create service: %s' % e.message
    puts 'Did you specify the key file in util.rb?'
    exit
  rescue Signet::AuthorizationError => e
    puts e.message
    puts 'Did you set the correct Service Account Email in util.rb?'
    exit
  end

  main(service, body)
end
