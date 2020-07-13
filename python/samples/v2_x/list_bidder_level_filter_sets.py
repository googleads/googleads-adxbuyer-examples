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

"""This example lists bidder-level filter sets."""


import argparse
import os
import pprint
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


_OWNER_NAME_TEMPLATE = 'bidders/%s'

DEFAULT_BIDDER_RESOURCE_ID = 154122622


def main(ad_exchange_buyer, owner_name):
  try:
    # Construct and execute the request.
    filter_sets = ad_exchange_buyer.bidders().filterSets().list(
        ownerName=owner_name).execute()
    print('Listing FilterSets for bidder: "%s".' % (owner_name))
    pprint.pprint(filter_sets)
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
      description='Creates a bidder-level filter set with the specified options'
  )
  # Required fields.
  parser.add_argument(
      '-b', '--bidder_resource_id', default=DEFAULT_BIDDER_RESOURCE_ID,
      help=('The resource ID of the bidders resource for which the filter '
            'sets were created. This will be used to construct the ownerName '
            'used as a path parameter for filter set requests. For additional '
            'information on how to configure the ownerName path parameter, '
            'see: https://developers.google.com/authorized-buyers/apis/'
            'reference/rest/v2beta1/bidders.filterSets/list'
            '#body.PATH_PARAMETERS.owner_name'))

  args = parser.parse_args()

  try:
    service = samples_util.GetService(version='v2beta1')
  except IOError:
    print('Unable to create adexchangebuyer service - %s')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, _OWNER_NAME_TEMPLATE % args.bidder_resource_id)

