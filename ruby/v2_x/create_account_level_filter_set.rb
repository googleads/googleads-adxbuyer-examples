#!/usr/bin/env ruby
# Encoding: utf-8
#
# Copyright:: Copyright 2017, Google Inc. All Rights Reserved.
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
# Creates a new account-level filter set.
#
# An account-level filter set can be used to retrieve data for a specific
# Authorized Buyers account, whether that be a bidder or child seat account.
#
# To get account-level filter sets, run list_account_level_filter_sets.rb.
#
# Tags: Bidders.Accounts.FilterSets.create

require 'date'
require 'optparse'

require_relative '../samples_util'


def create_account_level_filter_set(
    ad_exchange_buyer, owner_name, new_filter_set, is_transient)
  begin
    filter_set = ad_exchange_buyer.create_bidder_account_filter_set(
        owner_name, new_filter_set, is_transient: is_transient)
    abs_date_range = filter_set.absolute_date_range
    start_date = abs_date_range.start_date
    end_date = abs_date_range.end_date

    puts 'New filter set added successfully for account %s:' % owner_name
    puts '* Filter set name: %s' % filter_set.name
    puts "\tStart date: %s-%s-%s" % [
        start_date.year, start_date.month, start_date.day
    ]
    puts "\tEnd date: %s-%s-%s" % [end_date.year, end_date.month, end_date.day]
    puts "\tCreative ID: %s" % filter_set.creative_id unless
        filter_set.creative_id.nil?
    puts "\tDeal ID: %s" % filter_set.deal_id unless filter_set.deal_id.nil?
    puts "\tTime series granularity: %s" % filter_set.time_series_granularity \
        unless filter_set.time_series_granularity.nil?
    puts "\tFormat: %s" % filter_set.format unless filter_set.format.nil?
    puts "\tEnvironment: %s" % filter_set.environment unless
        filter_set.environment.nil?
    puts "\tPlatforms: %s" % filter_set.platforms.inspect unless
        filter_set.platforms.nil?
    puts "\tSeller network IDs: %s" % filter_set.seller_network_ids.inspect \
        unless filter_set.seller_network_ids.nil?
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
    service = get_service(ADEXCHANGEBUYER_V2BETA1)
  rescue ArgumentError => e
    raise 'Unable to create service, with error message: %s' % e.message
  rescue Signet::AuthorizationError => e
    raise ('Unable to create service, was the KEY_FILE in samples_util.rb ' +
           'set? Error message: %s') % e.message
  end

  # Set options and default values for fields used in this example.
  options = [
    Option.new(
      'bidder_resource_id',
      ('The resource ID of the bidders resource for which the filter set ' +
       'is being created. This will be used to construct the ownerName ' +
       'used as a path parameter for filter set requests. For additional ' +
       'information on how to configure the ownerName path parameter, see: ' +
       'https://developers.google.com/authorized-buyers/apis/reference/rest/' +
       'v2beta1/bidders.filterSets/create#body.PATH_PARAMETERS.owner_name'),
      :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'account_resource_id',
      ('The resource ID of the bidders.accounts resource for which the ' +
       'filter set is being created. This will be used to construct the ' +
       'ownerName used as a path parameter for filter set requests. For ' +
       'additional information on how to configure the ownerName path ' +
       'parameter, see: https://developers.google.com/authorized-buyers/' +
       'apis/reference/rest/v2beta1/bidders.accounts.filterSets/create' +
       '#body.PATH_PARAMETERS.owner_name'),
      :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'resource_id',
      ('The resource ID of the filter set. Note that this must be unique. ' +
       'This will be used to construct the filter set\'s name. for ' +
       'additional information on how to configure a filter set\'s name, ' +
       'see: https://developers.google.com/authorized-buyers/apis/reference/' +
       'rest/v2beta1/bidders.filterSets#FilterSet.FIELDS.name'),
      :short_alias => 'r', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'end_date',
      ('The end date for the filter set\'s absoluteDateRange field, which ' +
       'will be accepted in this example in YYYY-MM-DD format.'),
      :required => true,
      :default_value => Date.today().to_s()  # Insert default value here.
    ),
    Option.new(
      'start_date',
      ('The start date for the filter set\'s absoluteDateRange field, which ' +
       'will be accepted in this example in YYYYMMDD format.'),
      :required => true,
      :default_value => (Date.today() - 7).to_s()  # Insert default value here.
    ),
    Option.new(
      'creative_id', 'The ID of the creative on which to filter.',
      :short_alias => 'c', :required => false,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'deal_id', 'The ID of the deal on which to filter.',
      :short_alias => 'd', :required => false,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'environment', 'The environment on which to filter.',
      :short_alias => 'e', :required => false, :valid_values => ['WEB', 'APP'],
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'format', 'The format on which to filter.',
      :short_alias => 'f', :required => false,
      :valid_values => ['DISPLAY', 'VIDEO'],
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'platforms',
      ('The platforms on which to filter. The filters represented by ' +
       'multiple platforms are ORed together. Note that you may specify ' +
       'more than one using a comma as a delimiter.'),
      :type => Array, :short_alias => 'f', :required => false,
      :valid_values => ['DESKTOP', 'TABLET', 'MOBILE'],
      :default_value => []  # Insert default value here.
    ),
    Option.new(
      'seller_network_ids',
      ('The list of IDs for seller networks on which to filter. The filters ' +
       'represented by multiple seller network IDs are ORed together. Note ' +
       'that you may specify more than one using a comma as a delimiter.'),
      :type => Array, :short_alias => 's', :required => false,
      :default_value => []  # Insert default value here.
    ),
    Option.new(
      'time_series_granularity',
      ('The granularity of time intervals if a time series breakdown is ' +
       'desired.'),
      :short_alias => 't', :required => false,
      :valid_values => ['HOURLY', 'DAILY'],
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'is_transient',
      ('Whether the filter set is transient, or should be persisted '
       'indefinitely. In this example, this will default to true.'),
      :valid_values => ['true', 'false'], :required => false,
      :default_value => 'true'
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  owner_name = "bidders/%s/accounts/%s" % [opts['bidder_resource_id'],
                                           opts['account_resource_id']]
  resource_name = "%s/filterSets/%s" % [owner_name, opts['resource_id']]

  ed = Date.parse(opts['end_date'])
  sd = Date.parse(opts['start_date'])

  throw ArgumentError.new("Start date must be before the end date.") if sd > ed

  start_date = Google::Apis::Adexchangebuyer2V2beta1::Date.new(
      year: sd.year,
      month: sd.mon,
      day: sd.mday
  )
  end_date = Google::Apis::Adexchangebuyer2V2beta1::Date.new(
      year: ed.year,
      month: ed.mon,
      day: ed.mday
  )
  absolute_date_range =
      Google::Apis::Adexchangebuyer2V2beta1::AbsoluteDateRange.new(
          start_date: start_date,
          end_date: end_date
      )

  new_filter_set = Google::Apis::Adexchangebuyer2V2beta1::FilterSet.new(
      name: resource_name,
      absolute_date_range: absolute_date_range,
      creative_id: opts['creative_id'],
      deal_id: opts['deal_id'],
      time_series_granularity: opts['time_series_granularity'],
      format: opts['format'],
      environment: opts['environment'],
      platforms: opts['platforms'],
      seller_network_ids: opts['seller_network_ids']
  )

  is_transient = opts['is_transient'].casecmp('true') == 0

  create_account_level_filter_set(
      service, owner_name, new_filter_set, is_transient)
end
