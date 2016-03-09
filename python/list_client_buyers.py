#!/usr/bin/python
#
# Copyright 2016 Google Inc. All Rights Reserved.
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

"""This example lists the client buyers for a given account."""


import argparse
import pprint
import sys

from apiclient.errors import HttpError
from oauth2client.client import AccessTokenRefreshError
import samples_util


DEFAULT_ACCOUNT_ID = 'ENTER_ACCOUNT_ID_HERE'


def main(ad_exchange_buyer, account_id):
  try:
    # Construct and execute the request.
    clients = ad_exchange_buyer.accounts().clients().list(
        accountId=account_id).execute()
    print 'Client buyers for account ID: %d' % account_id
    pprint.pprint(clients)
  except HttpError as e:
    print e


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
      description='Lists client buyers for a given Ad Exchange account id.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help=('The integer id of the Ad Exchange account.'))
  args = parser.parse_args()

  try:
    service = samples_util.GetService(version='v2beta1')
  except IOError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you specify the key file in samples_util.py?'
    sys.exit()
  except AccessTokenRefreshError, ex:
    print 'Unable to create adexchangebuyer service - %s' % ex
    print 'Did you set the correct Service Account Email in samples_util.py?'
    sys.exit()

  main(service, args.account_id)

