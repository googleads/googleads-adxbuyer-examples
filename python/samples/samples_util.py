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

"""Utilities used by the Authorized Buyers Ad Exchange Buyer API Samples."""


import os

from googleapiclient.discovery import build
from googleapiclient.discovery import build_from_document
from google.auth.transport.requests import AuthorizedSession
from google.oauth2 import service_account


# Update these with the values for your Service Account found in the Google
# Developers Console.
KEY_FILE = 'INSERT_PATH_TO_KEY_FILE'  # Path to Service Account JSON key file.
# The maximum number of results to be returned in a page for any list response.
MAX_PAGE_SIZE = 50
# Ad Exchange Buyer API authorization scope.
SCOPE = 'https://www.googleapis.com/auth/adexchange.buyer'
ADEXCHANGEBUYER_VERSIONS = ('v1.2', 'v1.3', 'v1.4')
ADEXCHANGEBUYERII_VERSIONS = ('v2beta1',)
# Set the default version of Ad Exchange Buyer API to use.
DEFAULT_VERSION = ADEXCHANGEBUYER_VERSIONS[-1]

_API_NAME = 'adexchangebuyer2'
_DEFAULT_DISCOVERY_PATH = os.path.join(os.path.os.path.dirname(
    os.path.realpath(__file__)), 'discovery.json')
_LEGACY_API_NAME = 'adexchangebuyer'


def _IsLegacy(discovery_rest_url):
  """Returns whether the given discovery URL uses legacy discovery.

  Args:
    discovery_rest_url: a str containing a discovery URL for an API.

  Returns:
    True if the discovery URL is determined to be a legacy, otherwise False.
  """
  if 'googleapis.com/$discovery' in discovery_rest_url:
    return False
  return True


def _GetCredentials():
  """Steps through Service Account OAuth 2.0 flow to retrieve credentials."""
  return service_account.Credentials.from_service_account_file(
      KEY_FILE, scopes=[SCOPE])


def DownloadDiscoveryDocument(api_name, version, path=_DEFAULT_DISCOVERY_PATH,
                              label=None):
  """Downloads a discovery document for the given api_name and version.

  This utility assumes that the API for which a discovery document is being
  retrieved is publicly accessible. However, you may access allowlisted
  resources for a public API if you are added to its allowlist and specify the
  associated label.

  Args:
    api_name: a str indicating the name of the API for which a discovery
        document is to be downloaded.
    version: a str indicating the version number of the API.
    path: a str indicating the path to which you want to save the downloaded
        discovery document.
    label: a str indicating a label to be applied to the discovery service
        request. This is not applicable when downloading the discovery document
        of a legacy API. For non-legacy APIs, this may be used as a means of
        programmatically retrieving a copy of a discovery document containing
        allowlisted content.

  Raises:
    ValueError: if labels are specified for a legacy API, which is incompatible
    with labels.
  """
  credentials = _GetCredentials()
  auth_session = AuthorizedSession(credentials)
  discovery_service = build('discovery', 'v1')
  discovery_rest_url = None

  discovery_response = discovery_service.apis().list(
      name=api_name).execute()

  if 'items' in discovery_response:
    for api in discovery_response['items']:
      if api['version'] == version:
        discovery_rest_url = api['discoveryRestUrl']
        break

  if discovery_rest_url:
    is_legacy = _IsLegacy(discovery_rest_url)
  else:
    raise ValueError(f'API with name {api_name} and version {version} was not '
                     'found.')

  if all((is_legacy, label)):
    raise ValueError('The discovery URL associated with the api_name '
                     f'{api_name} and version {version} is for a legacy API. '
                     'These are not compatible with labels.')

  if label:
    # Apply the label query parameter if it exists.
    path_params = '&labels=%s' % label
    discovery_rest_url += path_params

  discovery_response = auth_session.get(discovery_rest_url)

  if discovery_response.status_code == 200:
    with open(path, 'wb') as handler:
      handler.write(discovery_response.text)
  else:
    raise ValueError('Unable to retrieve discovery document for api name '
                     f'{api_name} and version {version} via discovery URL: '
                     f'{discovery_rest_url}')


def GetService(version=DEFAULT_VERSION):
  """Builds the adexchangebuyer service used for the REST API.

  Args:
    version: a str indicating the Authorized Buyers Ad Exchange version to be
        retrieved. Depending on the version specified, either the v1 or
        the v2 API will be used.

  Returns:
    A googleapiclient.discovery.Resource instance used to interact with the
    Ad Exchange Buyer API. This will be for either v1 or the v2 API depending on
    the specified version.

  Raises:
    ValueError: raised if the specified version is not a valid version of either
    the v1 or v2 Ad Exchange Buyer API.
  """
  credentials = _GetCredentials()

  if version in ADEXCHANGEBUYER_VERSIONS:
    # Initialize client for Ad Exchange Buyer API v1
    service = build(_LEGACY_API_NAME, version, credentials=credentials)
  elif version in ADEXCHANGEBUYERII_VERSIONS:
    # Initialize client for Ad Exchange Buyer API v2
    service = build(_API_NAME, version, credentials=credentials)
  else:
    supported_versions = ', '.join(ADEXCHANGEBUYER_VERSIONS +
                                   ADEXCHANGEBUYERII_VERSIONS)
    raise ValueError('Invalid version provided. Supported versions are: '
                     f'{supported_versions}')

  return service


def GetServiceFromFile(discovery_file):
  """Builds a service using the specified discovery document.

  Args:
    discovery_file: a str path to the JSON discovery file for the service to be
        created.

  Returns:
    A googleapiclient.discovery.Resource instance used to interact with the
    service specified by the discovery_file.
  """
  credentials = _GetCredentials()

  with open(discovery_file, 'r') as handler:
    discovery_doc = handler.read()

  service = build_from_document(service=discovery_doc, credentials=credentials)

  return service

