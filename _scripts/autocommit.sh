#!/bin/bash

# ==============================================================================
# AUTOCOMMIT SCRIPT - Conventional Commits with Smart Messages
# ==============================================================================
# What?    Automatically commits changes every 5 minutes using cron
# For?     Maintain continuous backup of work during bootcamp sessions
# Impact?  Prevents loss of work, creates detailed git history for review
# ==============================================================================

set -e

# Configuration
REPO_PATH="/home/epti/Documents/epti-dev/bc-channel/bc-javaweb-sb"
LOG_FILE="${REPO_PATH}/_scripts/autocommit.log"
MAX_LOG_LINES=1000

# Colors for terminal output (disabled in cron)
if [ -t 1 ]; then
    RED='\033[0;31m'
    GREEN='\033[0;32m'
    YELLOW='\033[0;33m'
    BLUE='\033[0;34m'
    NC='\033[0m'
else
    RED=''
    GREEN=''
    YELLOW=''
    BLUE=''
    NC=''
fi

# ==============================================================================
# FUNCTIONS
# ==============================================================================

log_message() {
    local level="$1"
    local message="$2"
    local timestamp
    timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    echo "[${timestamp}] [${level}] ${message}" >> "$LOG_FILE"

    # Rotate log if too large
    if [ -f "$LOG_FILE" ]; then
        local line_count
        line_count=$(wc -l < "$LOG_FILE")
        if [ "$line_count" -gt "$MAX_LOG_LINES" ]; then
            tail -n $((MAX_LOG_LINES / 2)) "$LOG_FILE" > "${LOG_FILE}.tmp"
            mv "${LOG_FILE}.tmp" "$LOG_FILE"
        fi
    fi
}

# Detect the type of changes and generate conventional commit message
generate_commit_message() {
    local staged_files
    staged_files=$(git diff --cached --name-only)
    local commit_type=""
    local scope=""
    local description=""
    local what=""
    local for_what=""
    local impact=""

    # Count file types
    local docs_count=0
    local java_count=0
    local test_count=0
    local config_count=0
    local script_count=0
    local assets_count=0
    local github_count=0
    local vscode_count=0

    docs_count=$(echo "$staged_files" | grep -cE '\.(md|txt|adoc)$|README|LICENSE|CONTRIBUTING|CODE_OF_CONDUCT|SECURITY' 2>/dev/null || echo "0")
    java_count=$(echo "$staged_files" | grep -cE '\.java$' 2>/dev/null || echo "0")
    test_count=$(echo "$staged_files" | grep -cE '(Test|Spec)\.java$|src/test/' 2>/dev/null || echo "0")
    config_count=$(echo "$staged_files" | grep -cE '\.(properties|yml|yaml|xml|json)$|Dockerfile|docker-compose|\.gitignore' 2>/dev/null || echo "0")
    script_count=$(echo "$staged_files" | grep -cE '\.(sh|bash|zsh)$|_scripts/' 2>/dev/null || echo "0")
    assets_count=$(echo "$staged_files" | grep -cE '\.(svg|png|jpg|jpeg|gif|ico)$|assets/' 2>/dev/null || echo "0")
    github_count=$(echo "$staged_files" | grep -cE '\.github/' 2>/dev/null || echo "0")
    vscode_count=$(echo "$staged_files" | grep -cE '\.vscode/' 2>/dev/null || echo "0")

    # Detect week folder changes
    local week_folder
    week_folder=$(echo "$staged_files" | grep -oE 'week-[0-9]+' | head -1 || echo "")

    # Determine commit type based on changed files
    if [ "$test_count" -gt 0 ] && [ "$java_count" -eq "$test_count" ]; then
        commit_type="test"
        scope="testing"
        what="test files"
        for_what="improve code coverage and reliability"
        impact="better test coverage ensures code quality"
    elif [ "$docs_count" -gt 0 ] && [ "$java_count" -eq 0 ]; then
        commit_type="docs"
        if [ -n "$week_folder" ]; then
            scope="$week_folder"
            what="documentation for $week_folder"
        else
            scope="readme"
            what="project documentation"
        fi
        for_what="provide learning materials and guidance"
        impact="improved documentation helps learners understand concepts"
    elif [ "$config_count" -gt 0 ] && [ "$java_count" -eq 0 ]; then
        commit_type="chore"
        scope="config"
        what="configuration files"
        for_what="maintain project setup and dependencies"
        impact="consistent development environment across team"
    elif [ "$script_count" -gt 0 ]; then
        commit_type="chore"
        scope="scripts"
        what="automation scripts"
        for_what="improve development workflow"
        impact="streamlined development process"
    elif [ "$github_count" -gt 0 ]; then
        commit_type="ci"
        scope="github"
        what="GitHub workflows and templates"
        for_what="automate CI/CD and standardize contributions"
        impact="consistent project management and automation"
    elif [ "$vscode_count" -gt 0 ]; then
        commit_type="chore"
        scope="vscode"
        what="VS Code configuration"
        for_what="standardize development environment"
        impact="consistent coding experience for all developers"
    elif [ "$assets_count" -gt 0 ]; then
        commit_type="chore"
        scope="assets"
        what="visual assets and images"
        for_what="enhance documentation and UI"
        impact="better visual communication"
    elif [ "$java_count" -gt 0 ]; then
        # Detect if it's a new feature, fix, or refactor
        local added_lines
        local deleted_lines
        added_lines=$(git diff --cached --numstat | awk '{sum += $1} END {print sum+0}')
        deleted_lines=$(git diff --cached --numstat | awk '{sum += $2} END {print sum+0}')

        if [ "$added_lines" -gt "$deleted_lines" ]; then
            commit_type="feat"
            what="new functionality"
            for_what="extend application capabilities"
            impact="new features available for users"
        else
            commit_type="refactor"
            what="code improvements"
            for_what="improve code quality and maintainability"
            impact="cleaner, more maintainable codebase"
        fi

        if [ -n "$week_folder" ]; then
            scope="$week_folder"
        else
            scope=$(echo "$staged_files" | grep '\.java$' | head -1 | sed 's|.*/\([^/]*\)/[^/]*\.java|\1|' | head -1 || echo "")
        fi
    else
        commit_type="chore"
        scope="misc"
        what="miscellaneous updates"
        for_what="general project maintenance"
        impact="project kept up to date"
    fi

    # Generate description based on actual changes
    local file_count
    local first_file
    file_count=$(echo "$staged_files" | wc -l)
    first_file=$(echo "$staged_files" | head -1 | xargs basename 2>/dev/null || echo "files")

    if [ "$file_count" -eq 1 ]; then
        description="update ${first_file}"
    elif [ "$file_count" -le 3 ]; then
        description="update ${file_count} files including ${first_file}"
    else
        description="update ${file_count} files in ${scope:-project}"
    fi

    # Build commit message with what/for/impact
    local commit_msg="${commit_type}"
    if [ -n "$scope" ]; then
        commit_msg="${commit_msg}(${scope})"
    fi
    commit_msg="${commit_msg}: ${description}

What?   ${what}
For?    ${for_what}
Impact? ${impact}

---
Auto-committed at $(date '+%Y-%m-%d %H:%M:%S')
Changed files: ${file_count}"

    echo "$commit_msg"
}

# ==============================================================================
# MAIN SCRIPT
# ==============================================================================

main() {
    cd "$REPO_PATH" || {
        log_message "ERROR" "Cannot access repository at $REPO_PATH"
        exit 1
    }

    # Check if it's a git repository
    if [ ! -d ".git" ]; then
        log_message "ERROR" "Not a git repository: $REPO_PATH"
        exit 1
    fi

    # Check for changes
    if git diff --quiet && git diff --cached --quiet; then
        log_message "INFO" "No changes to commit"
        echo -e "${BLUE}ℹ${NC} No changes to commit"
        exit 0
    fi

    # Stage all changes
    git add -A

    # Check if there are staged changes
    if git diff --cached --quiet; then
        log_message "INFO" "No staged changes after git add"
        echo -e "${BLUE}ℹ${NC} No staged changes"
        exit 0
    fi

    # Generate commit message
    local commit_message
    commit_message=$(generate_commit_message)

    # Commit changes
    if git commit -m "$commit_message"; then
        local short_hash
        local commit_type
        short_hash=$(git rev-parse --short HEAD)
        commit_type=$(echo "$commit_message" | head -1 | cut -d':' -f1)
        log_message "SUCCESS" "Committed: $short_hash - $commit_type"
        echo -e "${GREEN}✓${NC} Committed: ${YELLOW}$short_hash${NC} - $commit_type"

        # Show commit summary
        echo -e "${BLUE}─────────────────────────────────────${NC}"
        echo "$commit_message" | head -6
        echo -e "${BLUE}─────────────────────────────────────${NC}"
    else
        log_message "ERROR" "Failed to commit changes"
        echo -e "${RED}✗${NC} Failed to commit changes"
        exit 1
    fi
}

# Run main function
main "$@"
