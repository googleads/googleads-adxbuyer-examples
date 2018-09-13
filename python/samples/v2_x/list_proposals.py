#!/usr/bin/python
#
# Copyright 2018 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""This example lists Marketplace proposals available to a given accountId.

By default, this example will list all proposals accessible to the Authorized
Buyers account.

You can use the --filter argument to target specific proposals. For more
details, see:
https://developers.google.com/authorized-buyers/apis/guides/v2/list_filters
"""


import argparse
import os
import pprint
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID_HERE'


def main(ad_exchange_buyer, account_id, filter, filter_syntax):
  try:
    response = ad_exchange_buyer.accounts().proposals().list(
        accountId=account_id, filter=filter,
        filterSyntax=filter_syntax).execute()
    proposals = response.get('proposals')
    if proposals:
      print 'Proposals found for accountId "%s":' % account_id
      for proposal in proposals:
        pprint.pprint(proposal)
        print
    else:
      print 'No proposals found for accountId "%s"' % account_id
  except HttpError as e:
    print 'Error returned when listing proposals with filter "%s"' % filter
    print e


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
      description='Lists proposals for the given buyer accountId.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help='The integer id of the Authorized Buyers account.')
  parser.add_argument(
      '-f', '--filter', default='',
      help='Optional filter used to filter the Proposals.')
  parser.add_argument(
      '-s', '--filter_syntax', default='LIST_FILTER',
      help=('Optional Enum value specifying whether the given filter\'s syntax '
            'is PQL or LIST_FILTER. PQL is the default syntax used if none is '
            'specified; however, this example will default to LIST_FILTER.'))
  args = parser.parse_args()

  try:
    service = samples_util.GetService('v2beta1')
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit(1)

  main(service, args.account_id, args.filter, args.filter_syntax)

