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
# Inserts a PretargetingConfig with the given values. To get Account IDs, run
# list_accounts.rb.
#
# Tags: PretargetingConfig.insert

require_relative 'util'


def main(ad_exchange_buyer, params, body)
  request = ad_exchange_buyer.pretargeting_config.insert(params)
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
        :configName => 'INSERT_CONFIG_NAME',
        :isActive => 'INSERT_TRUE_OR_FALSE',
        :creativeType => ['INSERT_CREATIVE_TYPE'],
        :dimensions => [
            {
                :width => 'INSERT_WIDTH_HERE'.to_i,
                :height => 'INSERT_HEIGHT_HERE'.to_i
        }]
    }

    params = {
        :accountId => 'INSERT_ACCOUNT_ID'.to_i
    }

    if params[:accountId] == 0
      raise 'You need to set the accountId!'
    end

    if body[:configName] === 'INSERT_CONFIG_NAME'
      raise 'You need to set configName!'
    end

    if body[:isActive] === 'INSERT_TRUE_OR_FALSE'
      raise 'You need to set isActive!'
    end

    if body[:creativeType] === 'INSERT_CREATIVE_TYPE'
      raise 'You need to set the creativeType!'
    end

    if body[:dimensions][0][:width] == 0
      raise 'You need to set the width!!'
    end

    if body[:dimensions][0][:height] == 0
      raise 'You need to set the height!'
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

  main(service, params, body)
end
