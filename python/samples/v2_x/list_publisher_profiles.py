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

"""This example lists Marketplace publisher profiles."""


import argparse
import os
import pprint
import sys

sys.path.insert(0, os.path.abspath('..'))

from googleapiclient.errors import HttpError
import samples_util


DEFAULT_ACCOUNT_ID = 'INSERT_ACCOUNT_ID_HERE'


def main(ad_exchange_buyer, account_id):
  try:
    pub_profiles = ad_exchange_buyer.accounts().publisherProfiles().list(
        accountId=account_id).execute().get('publisherProfiles')
    if pub_profiles:
      print('Publisher profiles found:')
      for pub_profile in pub_profiles:
        pprint.pprint(pub_profile)
        print()
    else:
      print('No publisher profiles found.')
  except HttpError as e:
    print(e)


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
      description='Lists all publisher profiles.')
  parser.add_argument(
      '-a', '--account_id', default=DEFAULT_ACCOUNT_ID, type=int,
      help='The integer id of the Authorized Buyers account.')
  args = parser.parse_args()

  try:
    service = samples_util.GetService('v2beta1')
  except IOError as ex:
    print(f'Unable to create adexchangebuyer service - {ex}')
    print('Did you specify the key file in samples_util.py?')
    sys.exit(1)

  main(service, args.account_id)

