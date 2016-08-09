# akka-api-gateway-example [![CircleCI](https://circleci.com/gh/dazzle-lab/akka-api-gateway-example.svg?style=svg&circle-token=ce179ef36ee75c3c8ee4ad3a5c9d16c045daf196)](https://circleci.com/gh/dazzle-lab/akka-api-gateway-example)

Implement api gateway (reverse proxy) example using akka-http-experimental.

## Requirements

* Java 8+ (Scala 2.11.8)

## Usage

Launch http server.

```
$ sbt run
```

```
$ curl -s http://localhost:8080/v1/github/users/dakatsuka
{
  "login": "dakatsuka",
  "id": 59034,
  "avatar_url": "https://avatars.githubusercontent.com/u/59034?v=3",
  "gravatar_id": "",
  "url": "https://api.github.com/users/dakatsuka",
  "html_url": "https://github.com/dakatsuka",
  "followers_url": "https://api.github.com/users/dakatsuka/followers",
  "following_url": "https://api.github.com/users/dakatsuka/following{/other_user}",
  "gists_url": "https://api.github.com/users/dakatsuka/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/dakatsuka/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/dakatsuka/subscriptions",
  "organizations_url": "https://api.github.com/users/dakatsuka/orgs",
  "repos_url": "https://api.github.com/users/dakatsuka/repos",
  "events_url": "https://api.github.com/users/dakatsuka/events{/privacy}",
  "received_events_url": "https://api.github.com/users/dakatsuka/received_events",
  "type": "User",
  "site_admin": false,
  "name": "Dai Akatsuka",
  "company": "Dazzle Inc.",
  "blog": "http://blog.dakatsuka.jp/",
  "location": "Tokyo, Japan",
  "email": "d.akatsuka@gmail.com",
  "hireable": null,
  "bio": null,
  "public_repos": 54,
  "public_gists": 123,
  "followers": 72,
  "following": 138,
  "created_at": "2009-03-01T07:55:33Z",
  "updated_at": "2016-07-28T10:05:11Z"
} 
```

## Test

```
$ sbt test
```
