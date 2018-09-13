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
# Lists bid metrics for a given account.
#
# Tags: Bidders.Accounts.FilterSets.BidMetrics.list

require 'optparse'

require_relative '../samples_util'


def print_metric(metric_name, metric)
  puts "\t%s:" % metric_name
  puts "\t\tValue: %s" % (metric.value.nil? ? 0 : metric.value)
  puts "\t\tVariance: %s" % (metric.variance.nil? ? 0 : metric.variance)
end


def list_account_level_bid_metrics(
    ad_exchange_buyer, filter_set_name, page_size)
  begin
    page_token = nil

    puts 'Found the following account-level bid metrics with filter set %s:' %
        filter_set_name

    begin
      response = ad_exchange_buyer.list_bidder_account_filter_set_bid_metrics(
          filter_set_name, page_size: page_size
      )

      response.bid_metrics_rows.each do |bid_metrics_row|
        time_interval = bid_metrics_row.row_dimensions.time_interval
        puts '* Bid metrics from %s - %s:' % [time_interval.start_time,
                                              time_interval.end_time]
        print_metric("Bids", bid_metrics_row.bids)
        print_metric("Bids in auction", bid_metrics_row.bids_in_auction)
        print_metric("Impressions won", bid_metrics_row.impressions_won)
        print_metric("Billed impressions", bid_metrics_row.billed_impressions)
        print_metric("Measurable impressions",
            bid_metrics_row.measurable_impressions)
        print_metric("Viewable impressions",
            bid_metrics_row.viewable_impressions)
      end

      page_token = response.next_page_token
    end while not page_token.nil?
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
      ('The resource ID of the bidders resource for which the filter ' +
       'sets were created. This will be used to construct the ownerName ' +
       'used as a path parameter for filter set requests. For additional ' +
       'information on how to configure the ownerName path parameter, ' +
       'see: https://developers.google.com/authorized-buyers/apis/' +
       'reference/rest/v2beta1/bidders.filterSets/list' +
       '#body.PATH_PARAMETERS.owner_name'),
      :short_alias => 'b', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'account_resource_id',
      ('The resource ID of the bidders.accounts resource for which the ' +
       'filter set was created. This will be used to construct the ' +
       'filterSetName used as a path parameter for bid metrics requests. ' +
       'For additional information on how to configure the filterSetName ' +
       'path parameter, see: https://developers.google.com/' +
       'authorized-buyers/apis/reference/rest/v2beta1/' +
       'bidders.accounts.filterSets.bidMetrics/list' +
       '#body.PATH_PARAMETERS.filter_set_name'),
      :short_alias => 'a', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'filter_set_resource_id',
      ('The resource ID of the filter set resource for which the bid ' +
       'metrics are being listed. This will be used to construct the ' +
       'filterSetName used as a path parameter for bid metrics requests. ' +
       'For additional information on how to configure the filterSetName ' +
       'path parameter, see: https://developers.google.com/' +
       'authorized-buyers/buyer-rest/reference/rest/v2beta1/' +
       'bidders.filterSets.bidMetrics/list' +
       '#body.PATH_PARAMETERS.filter_set_name'),
      :short_alias => 'f', :required => true,
      :default_value => nil  # Insert default value here.
    ),
    Option.new(
      'max_page_size',
      'The maximum number of entries returned on one result page.',
      :type => Integer, :short_alias => 'm', :required => true,
      :default_value => MAX_PAGE_SIZE
    )
  ]

  # Parse options.
  parser = Parser.new(options)
  opts = parser.parse(ARGV)

  filter_set_name = 'bidders/%s/accounts/%s/filterSets/%s' % [
      opts['bidder_resource_id'],
      opts['account_resource_id'],
      opts['filter_set_resource_id']
  ]

  list_account_level_bid_metrics(
      service, filter_set_name, opts['max_page_size']
  )
end
