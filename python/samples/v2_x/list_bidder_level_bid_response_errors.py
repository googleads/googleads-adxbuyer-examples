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

"""This example lists bidder-level bid respnse errors.

Additional RTB Troubleshooting resources that can be similarly used to retrieve
metrics for real-time bidding can be found nested under the bidders.filterSets
resource.
"""


import argparse
import os
import pprint
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


_FILTER_SET_NAME_TEMPLATE = 'bidders/%s/filterSets/%s'

DEFAULT_BIDDER_RESOURCE_ID = 154122622
DEFAULT_FILTER_SET_RESOURCE_ID = 'ENTER_FILTER_SET_RESOURCE_ID_HERE'


def main(ad_exchange_buyer, filter_set_name, page_size):
  page_token = None
  more_pages = True

  print('Listing bid metrics for filter set: "%s".' % filter_set_name)
  while more_pages:
    try:
      # Construct and execute the request.
      response = ad_exchange_buyer.bidders().filterSets().bidResponseErrors().list(
          filterSetName=filter_set_name, pageSize=page_size,
          pageToken=page_token).execute()
    except HttpError as e:
      print(e)

    pprint.pprint(response)

    page_token = response.get('nextPageToken')
    more_pages = bool(page_token)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
      description=('Lists bid metrics for the filterSetName specified by the '
                   'given bidder and filter set resource IDs.')
  )
  # Required fields.
  parser.add_argument(
      '-b', '--bidder_resource_id', default=DEFAULT_BIDDER_RESOURCE_ID,
      help=('The resource ID of the bidders resource for which the filter set '
            'was created. This will be used to construct the filterSetName '
            'used as a path parameter for bid metrics requests. For '
            'additional information on how to configure the filterSetName path'
            ' parameter, see: https://developers.google.com/authorized-buyers/'
            'apis/reference/rest/v2beta1/bidders.filterSets.bidMetrics/'
            'list#body.PATH_PARAMETERS.filter_set_name'))
  parser.add_argument(
      '-f', '--filter_set_resource_id', default=DEFAULT_FILTER_SET_RESOURCE_ID,
      help=('The resource ID of the filter set resource for which the bid '
            'metrics are being listed. This will be used to construct the '
            'filterSetName used as a path parameter for bid metrics requests. '
            'For additional information on how to configure the filterSetName '
            'path parameter, see: https://developers.google.com/'
            'authorized-buyers/buyer-rest/reference/rest/v2beta1/'
            'bidders.filterSets.bidMetrics/list'
            '#body.PATH_PARAMETERS.filter_set_name'))
  parser.add_argument(
      '-p', '--page_size', default=samples_util.MAX_PAGE_SIZE,
      help=('The number of rows to return per page. The server may return '
            'fewer rows than specified.'))

  args = parser.parse_args()

  try:
    service = samples_util.GetService(version='v2beta1')
  except IOError:
    print('Unable to create adexchangebuyer service - %s')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, _FILTER_SET_NAME_TEMPLATE % (args.bidder_resource_id,
                                             args.filter_set_resource_id),
       args.page_size)

