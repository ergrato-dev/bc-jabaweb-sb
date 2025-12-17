# Autocommit Scripts

## 📋 What?

Automation scripts for automatic git commits using **Conventional Commits** format with detailed context (What? For? Impact?).

## 🎯 For?

- **Bootcamp sessions**: Maintain continuous backup of work during 5-hour sessions
- **Prevent data loss**: Auto-save progress every 5 minutes
- **Learning history**: Create detailed git history for review and learning
- **Consistent commits**: Enforce conventional commit messages automatically

## 💥 Impact?

- No more lost work due to forgetting to commit
- Consistent commit history across all bootcamp participants
- Easy to review progress and changes over time
- Professional git habits from day one

---

## 🚀 Quick Start

### Install Autocommit (Every 5 Minutes)

```bash
./cron-setup.sh install
```

### Check Status

```bash
./cron-setup.sh status
```

### Stop Autocommit

```bash
./cron-setup.sh uninstall
```

### Manual Test

```bash
./cron-setup.sh test
```

### View Logs

```bash
./cron-setup.sh logs
```

---

## 📝 Commit Message Format

The autocommit script generates **Conventional Commits** with the following format:

```
<type>(<scope>): <description>

What?   <what was changed>
For?    <why it was changed>
Impact? <what effect does it have>

---
Auto-committed at YYYY-MM-DD HH:MM:SS
Changed files: N
```

### Commit Types

| Type | When Used |
|------|-----------|
| `feat` | New Java functionality (more additions than deletions) |
| `refactor` | Code improvements (more deletions than additions) |
| `docs` | Documentation changes (.md, README, etc.) |
| `test` | Test files only |
| `chore` | Configuration, scripts, assets |
| `ci` | GitHub workflows and templates |

### Scope Detection

The script automatically detects scope based on:

- **Week folders**: `week-01`, `week-02`, etc.
- **Config type**: `config`, `scripts`, `vscode`
- **GitHub files**: `github`
- **Java packages**: Extracted from file path

---

## 📁 Files

| File | Description |
|------|-------------|
| `autocommit.sh` | Main autocommit script with smart commit messages |
| `cron-setup.sh` | Cron job installer/manager |
| `autocommit.log` | Log of all autocommit operations |
| `autocommit-cron.log` | Cron execution output |
| `README.md` | This documentation |

---

## ⚙️ Configuration

Edit `autocommit.sh` to customize:

```bash
# Repository path
REPO_PATH="/home/epti/Documents/epti-dev/bc-channel/bc-javaweb-sb"

# Log file location
LOG_FILE="${REPO_PATH}/_scripts/autocommit.log"

# Max log lines before rotation
MAX_LOG_LINES=1000
```

---

## 🕐 Cron Schedule

Default: Every 5 minutes

```
*/5 * * * * /path/to/autocommit.sh
```

To change frequency, edit `cron-setup.sh`:

```bash
# Every 10 minutes
CRON_JOB="*/10 * * * * ${AUTOCOMMIT_SCRIPT}"

# Every hour
CRON_JOB="0 * * * * ${AUTOCOMMIT_SCRIPT}"

# Every 2 minutes (more aggressive)
CRON_JOB="*/2 * * * * ${AUTOCOMMIT_SCRIPT}"
```

---

## 🔧 Troubleshooting

### Cron not running?

1. Check if cron service is running:
   ```bash
   systemctl status cron
   ```

2. Check crontab:
   ```bash
   crontab -l
   ```

3. Check logs:
   ```bash
   ./cron-setup.sh logs
   ```

### Permission denied?

Make scripts executable:
```bash
chmod +x autocommit.sh cron-setup.sh
```

### Git not committing?

1. Ensure you're in a git repository
2. Check git config:
   ```bash
   git config user.name
   git config user.email
   ```

---

## 📊 Example Output

```
✓ Committed: a1b2c3d - chore(scripts)
─────────────────────────────────────
chore(scripts): update autocommit.sh

What?   automation scripts
For?    improve development workflow
Impact? streamlined development process
─────────────────────────────────────
```

---

## ⚠️ Important Notes

1. **Does NOT auto-push**: Only commits locally. Push manually or add to script.
2. **Respects .gitignore**: Won't commit ignored files.
3. **Log rotation**: Logs are automatically trimmed to prevent disk fill.
4. **No force push**: Safe operations only.

---

## 🎓 For Bootcamp Instructors

Enable autocommit at the start of each session:

```bash
cd /home/epti/Documents/epti-dev/bc-channel/bc-javaweb-sb/_scripts
./cron-setup.sh install
```

Disable at the end:

```bash
./cron-setup.sh uninstall
```

This ensures all student work is captured even if they forget to commit!
