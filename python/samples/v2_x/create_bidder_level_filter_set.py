#!/usr/bin/python
#
# Copyright 2017 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example creates a bidder-level filter set.

A bidder-level filter set can be used to retrieve aggregated data for all
Authorized Buyers accounts under the given bidder account, including the bidder
account itself.
"""


import argparse
from datetime import date
from datetime import datetime
from datetime import timedelta
import os
import pprint
import sys
import uuid

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


_DATE_FORMAT = '%Y%m%d'
_FILTER_SET_NAME_TEMPLATE = ('bidders/{bidders_resource_id}/'
                             'filterSets/{filtersets_resource_id}')
_OWNER_NAME_TEMPLATE = 'bidders/{bidders_resource_id}'
_TODAY = date.today()
_VALID_ENVIRONMENTS = ('WEB', 'APP')
_VALID_FORMATS = ('DISPLAY', 'VIDEO')
_VALID_PLATFORMS = ('DESKTOP', 'TABLET', 'MOBILE')
_VALID_TIME_SERIES_GRANULARITIES = ('HOURLY', 'DAILY')

DEFAULT_BIDDER_RESOURCE_ID = 'ENTER_BIDDER_RESOURCE_ID_HERE'
DEFAULT_FILTER_SET_RESOURCE_ID = f'FilterSet_{uuid.uuid4()}'
DEFAULT_END_DATE = _TODAY.strftime(_DATE_FORMAT)
DEFAULT_START_DATE = (_TODAY - timedelta(days=7)).strftime(
    _DATE_FORMAT)


def main(ad_exchange_buyer, owner_name, body, is_transient):
  try:
    # Construct and execute the request.
    filter_set = ad_exchange_buyer.bidders().filterSets().create(
        ownerName=owner_name, isTransient=is_transient, body=body).execute()
    print(f'FilterSet created for bidder: "{owner_name}".')
    pprint.pprint(filter_set)
  except HttpError as e:
    print(e)


if __name__ == '__main__':

  def time_series_granularity_type(s):
    if s not in _VALID_TIME_SERIES_GRANULARITIES:
      raise argparse.ArgumentTypeError('Invalid TimeSeriesGranularity '
                                       f'specified: "{s}".')
    return s

  def environment_type(s):
    if s not in _VALID_ENVIRONMENTS:
      raise argparse.ArgumentTypeError(
          f'Invalid Environment specified: "{s}".')
    return s

  def format_type(s):
    if s not in _VALID_FORMATS:
      raise argparse.ArgumentTypeError(f'Invalid Format specified: "{s}".')
    return s

  def platform_type(s):
    if s not in _VALID_PLATFORMS:
      raise argparse.ArgumentTypeError(f'Invalid Platform specified: "{s}".')
    return s

  def valid_date(s):
    try:
      return datetime.strptime(s, _DATE_FORMAT).date()
    except ValueError:
      raise argparse.ArgumentTypeError(f'Invalid date specified: "{s}".')

  parser = argparse.ArgumentParser(
      description=('Creates a bidder-level filter set with the specified '
                   'options.'))
  # Required fields.
  parser.add_argument(
      '-b', '--bidder_resource_id', default=DEFAULT_BIDDER_RESOURCE_ID,
      help=('The resource ID of the bidders resource for which the filter set '
            'is being created. This will be used to construct the ownerName '
            'used as a path parameter for filter set requests. For additional '
            'information on how to configure the ownerName path parameter, '
            'see: https://developers.google.com/authorized-buyers/apis/'
            'reference/rest/v2beta1/bidders.filterSets/create'
            '#body.PATH_PARAMETERS.owner_name'))
  parser.add_argument(
      '-r', '--resource_id', default=DEFAULT_FILTER_SET_RESOURCE_ID,
      help=('The resource ID of the filter set. Note that this must be '
            'unique. This will be used to construct the filter set\'s name. '
            'For additional information on how to configure a filter set\'s '
            'name, see: https://developers.google.com/authorized-buyers/apis/'
            'reference/rest/v2beta1/bidders.filterSets#FilterSet.FIELDS.name'))
  parser.add_argument(
      '--end_date', default=DEFAULT_END_DATE, type=valid_date,
      help=('The end date for the filter set\'s absoluteDateRange field, which '
            'will be accepted in this example in YYYYMMDD format.'))
  parser.add_argument(
      '--start_date', default=DEFAULT_START_DATE, type=valid_date,
      help=('The start date for the filter set\'s time_range field, which '
            'will be accepted in this example in YYYYMMDD format.'))
  # Optional fields.
  parser.add_argument(
      '-e', '--environment', required=False,
      type=environment_type,
      help=('The environment on which to filter.'))
  parser.add_argument(
      '-f', '--format', required=False,
      type=format_type,
      help=('The format on which to filter.'))
  parser.add_argument(
      '-p', '--platforms', required=False, nargs='*', type=platform_type,
      help=('The platforms on which to filter. The filters represented by '
            'multiple platforms are ORed together. Note that you may specify '
            'more than one using a space as a delimiter.'))
  parser.add_argument(
      '-s', '--seller_network_ids', required=False, nargs='*', type=int,
      help=('The list of IDs for seller networks on which to filter. The '
            'filters represented by multiple seller network IDs are ORed '
            'together. Note that you may specify more than one using a space '
            'as a delimiter.'))
  parser.add_argument(
      '-t', '--time_series_granularity', required=False,
      type=time_series_granularity_type,
      help=('The granularity of time intervals if a time series breakdown is '
            'desired.'))
  parser.add_argument(
      '--is_transient', required=False, default=True, type=bool,
      help=('Whether the filter set is transient, or should be persisted '
            'indefinitely. In this example, this will default to True.'))

  args = parser.parse_args()

  # Build the time_range as an AbsoluteDateRange.
  time_range = {
      'startDate': {
          'year': args.start_date.year,
          'month': args.start_date.month,
          'day': args.start_date.day
      },
      'endDate': {
          'year': args.end_date.year,
          'month': args.end_date.month,
          'day': args.end_date.day
      }
  }

  # Create a body containing the required fields.
  BODY = {
      'name': _FILTER_SET_NAME_TEMPLATE.format(
          bidders_resource_id=args.bidder_resource_id,
          filtersets_resource_id=args.resource_id),
      # Note: You may alternatively specify relativeDateRange or
      # realtimeTimeRange.
      'absoluteDateRange': time_range
  }

  # Add optional fields to body if specified.
  if args.environment:
    BODY['environment'] = args.environment
  if args.format:
    BODY['format'] = args.format
  if args.platforms:
    BODY['platforms'] = args.platforms
  if args.seller_network_ids:
    BODY['sellerNetworkIds'] = args.seller_network_ids
  if args.time_series_granularity:
    BODY['timeSeriesGranularity'] = args.time_series_granularity

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, _OWNER_NAME_TEMPLATE.format(
           bidders_resource_id=args.bidder_resource_id),
       BODY, args.is_transient)

