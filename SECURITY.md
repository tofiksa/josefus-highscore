# Security Policy

## Supported Versions
We support the latest main branch and the most recent tagged major/minor release. Older versions may not receive fixes.

| Version      | Supported          |
| ------------ | ------------------ |
| main         | ✅                 |
| latest tag   | ✅                 |
| anything older| ❌                |

## Reporting a Vulnerability
- Email: `security@josefus-highscore.invalid`
- Please include: affected endpoints, reproduction steps, expected vs. actual behavior, and any proof-of-concept if available.
- We aim to acknowledge reports within 3 business days.

## Handling Process
1. Triage and reproduce.
2. Assess impact and prioritize.
3. Prepare a fix and tests.
4. Release a patched version and communicate resolution.

## Guidance for Contributors
- Do not post vulnerabilities in public issues.
- Avoid committing secrets; use environment variables.
- Add tests for security fixes (authZ/authN, input validation, data access).
