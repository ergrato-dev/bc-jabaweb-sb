#!/bin/bash

# ==============================================================================
# CRON SETUP SCRIPT - Install/Uninstall Autocommit Cron Job
# ==============================================================================
# What?    Manages cron job for automatic commits every 5 minutes
# For?     Easy setup and removal of autocommit automation
# Impact?  One command to enable/disable automatic git backups
# ==============================================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
AUTOCOMMIT_SCRIPT="${SCRIPT_DIR}/autocommit.sh"
CRON_COMMENT="# bc-javaweb-sb autocommit"
CRON_JOB="*/5 * * * * ${AUTOCOMMIT_SCRIPT} >> ${SCRIPT_DIR}/autocommit-cron.log 2>&1"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

show_banner() {
    echo -e "${CYAN}"
    echo "╔═══════════════════════════════════════════════════════════╗"
    echo "║           AUTOCOMMIT CRON MANAGER                        ║"
    echo "║     Conventional Commits Every 5 Minutes                 ║"
    echo "╚═══════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

show_usage() {
    echo -e "${YELLOW}Usage:${NC}"
    echo "  $0 install    - Install cron job (every 5 minutes)"
    echo "  $0 uninstall  - Remove cron job"
    echo "  $0 status     - Check if cron job is active"
    echo "  $0 test       - Run autocommit once (manual test)"
    echo "  $0 logs       - View recent autocommit logs"
    echo ""
    echo -e "${YELLOW}Examples:${NC}"
    echo "  $0 install    # Start auto-committing every 5 min"
    echo "  $0 status     # Check if it's running"
    echo "  $0 uninstall  # Stop auto-committing"
}

check_dependencies() {
    if ! command -v crontab &> /dev/null; then
        echo -e "${RED}✗${NC} crontab not found. Please install cron."
        exit 1
    fi

    if [ ! -f "$AUTOCOMMIT_SCRIPT" ]; then
        echo -e "${RED}✗${NC} autocommit.sh not found at: $AUTOCOMMIT_SCRIPT"
        exit 1
    fi
}

install_cron() {
    echo -e "${BLUE}Installing autocommit cron job...${NC}"

    # Make script executable
    chmod +x "$AUTOCOMMIT_SCRIPT"

    # Check if already installed
    if crontab -l 2>/dev/null | grep -q "bc-javaweb-sb autocommit"; then
        echo -e "${YELLOW}⚠${NC} Cron job already installed. Updating..."
        uninstall_cron_silent
    fi

    # Add new cron job
    (crontab -l 2>/dev/null | grep -v "bc-javaweb-sb autocommit"; echo "$CRON_COMMENT"; echo "$CRON_JOB") | crontab -

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓${NC} Cron job installed successfully!"
        echo ""
        echo -e "${CYAN}Schedule:${NC} Every 5 minutes"
        echo -e "${CYAN}Script:${NC}   $AUTOCOMMIT_SCRIPT"
        echo -e "${CYAN}Log:${NC}      ${SCRIPT_DIR}/autocommit-cron.log"
        echo ""
        echo -e "${YELLOW}ℹ${NC} The script will auto-commit changes with conventional commit messages"
        echo -e "${YELLOW}ℹ${NC} Each commit includes: What? For? Impact?"
    else
        echo -e "${RED}✗${NC} Failed to install cron job"
        exit 1
    fi
}

uninstall_cron_silent() {
    crontab -l 2>/dev/null | grep -v "bc-javaweb-sb autocommit" | crontab -
}

uninstall_cron() {
    echo -e "${BLUE}Removing autocommit cron job...${NC}"

    if ! crontab -l 2>/dev/null | grep -q "bc-javaweb-sb autocommit"; then
        echo -e "${YELLOW}⚠${NC} Cron job not found. Nothing to remove."
        exit 0
    fi

    uninstall_cron_silent

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓${NC} Cron job removed successfully!"
    else
        echo -e "${RED}✗${NC} Failed to remove cron job"
        exit 1
    fi
}

check_status() {
    echo -e "${BLUE}Checking autocommit cron status...${NC}"
    echo ""

    if crontab -l 2>/dev/null | grep -q "bc-javaweb-sb autocommit"; then
        echo -e "${GREEN}●${NC} Autocommit cron job is ${GREEN}ACTIVE${NC}"
        echo ""
        echo -e "${CYAN}Current cron entry:${NC}"
        crontab -l 2>/dev/null | grep -A1 "bc-javaweb-sb autocommit"
        echo ""

        # Show last log entries
        if [ -f "${SCRIPT_DIR}/autocommit.log" ]; then
            echo -e "${CYAN}Last 5 log entries:${NC}"
            tail -5 "${SCRIPT_DIR}/autocommit.log"
        fi
    else
        echo -e "${RED}○${NC} Autocommit cron job is ${RED}INACTIVE${NC}"
        echo ""
        echo -e "Run '${YELLOW}$0 install${NC}' to enable autocommit"
    fi
}

run_test() {
    echo -e "${BLUE}Running autocommit manually...${NC}"
    echo ""

    chmod +x "$AUTOCOMMIT_SCRIPT"
    "$AUTOCOMMIT_SCRIPT"
}

show_logs() {
    local log_file="${SCRIPT_DIR}/autocommit.log"
    local cron_log="${SCRIPT_DIR}/autocommit-cron.log"

    echo -e "${BLUE}Recent autocommit activity:${NC}"
    echo ""

    if [ -f "$log_file" ]; then
        echo -e "${CYAN}=== autocommit.log (last 20 lines) ===${NC}"
        tail -20 "$log_file"
    else
        echo -e "${YELLOW}⚠${NC} No autocommit.log found yet"
    fi

    echo ""

    if [ -f "$cron_log" ]; then
        echo -e "${CYAN}=== autocommit-cron.log (last 20 lines) ===${NC}"
        tail -20 "$cron_log"
    else
        echo -e "${YELLOW}⚠${NC} No autocommit-cron.log found yet"
    fi
}

# ==============================================================================
# MAIN
# ==============================================================================

show_banner
check_dependencies

case "${1:-}" in
    install)
        install_cron
        ;;
    uninstall|remove)
        uninstall_cron
        ;;
    status)
        check_status
        ;;
    test|run)
        run_test
        ;;
    logs|log)
        show_logs
        ;;
    *)
        show_usage
        exit 1
        ;;
esac
