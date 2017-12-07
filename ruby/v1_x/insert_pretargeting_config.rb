#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2015, Google Inc. All Rights Reserved.
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
# Inserts a PretargetingConfig with the given values.
#
# To get Account IDs, run list_accounts.rb.
#
# Tags: PretargetingConfig.insert

require 'optparse'

require_relative '../samples_util'


def insert_pretargeting_config(
    ad_exchange_buyer, account_id, new_pretargeting_config)
  begin
    pretargeting_config = ad_exchange_buyer.insert_pretargeting_config(
        account_id, new_pretargeting_config
    )

    puts 'New pretargeting config added successfully to account ID %d: '\
         % account_id
    puts "\tBilling ID: %s" % pretargeting_config.billing_id
    puts "\tConfig ID: %s" % pretargeting_config.config_id
    puts "\tConfig name: %s" % pretargeting_config.config_name
    puts "\tIs active: %s" % pretargeting_config.is_active
    puts "\tCreative Type: %s" % pretargeting_config.creative_type
    puts "\tDimensions:"
    pretargeting_config.dimensions.each do |dimension|
      puts "\t\tHeight: %d; Width: %d" % [dimension.height, dimension.width]
    end
  rescue Google::Apis::ServerError => e
    raise "The following server error occured:\n%s" % e.message
  rescue Google::Apis::ClientError => e
    raise "Invalid client request:\n%s" % e.message
  rescue Google::Apis::AuthorizationError => e
    raise "Authorization error occured:\n%s" % e.message
  end
end


if __FILE__ == $0
  begin
    # Retrieve the service used to make API requests.
    service = get_service()
  rescue ArgumentError => e
    raise 'Unable to create service, with error message: %s' % e.message
  rescue Signet::AuthorizationError => e
    raise ('Unable to create service, was the KEY_FILE in samples_util.rb ' +
           'set? Error message: %s') % e.message
  end

  # Set options and default values for fields used in this example.
  options = [
    Option.new(
      'account_id', 'The integer ID of the Ad Exchange buyer account.',
      :type => Integer, :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'config_name',
      'Name of the pretargeting config, should be unique',
      :short_alias => 'n', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'is_active', 'Whether the pretargeting config is active.',
      :type => FalseClass, :short_alias => 'i', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'creative_type',
      'The type of creatives allowed for the targeted ad slot.',
      :valid_values => ['PRETARGETING_CREATIVE_TYPE_HTML',
          'PRETARGETING_CREATIVE_TYPE_VIDEO'
      ],
      :short_alias => 't', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'height', 'The height of ad slots being targeted.',
      :type => Integer, :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'width', 'The width of ad slots being targeted.',
      :type => Integer, :required => true,
      :default_value => nil  # Insert default value here.
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  new_pretargeting_config = (
      Google::Apis::AdexchangebuyerV1_4::PretargetingConfig.new(
          config_name: opts['config_name'],
          is_active: opts['is_active'],
          creative_type: [opts['creative_type']],
          dimensions: [{:width => opts['width'], :height => opts['height']}]
      )
  )

  insert_pretargeting_config(
      service, opts['account_id'], new_pretargeting_config
  )
end
