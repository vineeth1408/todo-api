---
name: implement-issue
description: "Fetch GitHub issue, plan implementation, delegate to implementer, auto-review, push to main, close issue. Manual workflow with full control."
agent: agent
tools: [read, search, file_search, grep_search, semantic_search, runSubagent, mcp_github_issue_read, mcp_github_push_files, mcp_github_issue_write, mcp_github_get_copilot_job_status, runTests]
---

You are orchestrating a full issue implementation workflow for todo-api.

## Steps to follow in order:

### Step 1 - Fetch Issue
Use GitHub MCP tool (mcp_github_issue_read) to fetch the issue details provided by the user.
Read the issue title, body and labels carefully.

**Error handling**: If issue not found, retry with GH issue API or ask user to verify issue number.

### Step 2 - Plan
Based on the issue content, create a detailed implementation plan:
- Which files need to change
- Which layers are affected (entity, dto, service, controller)

Present the plan and wait for user approval before proceeding.
If user rejects, ask for refinement or close task
- No code yet, only plan
Present the plan and wait for user approval before proceeding.

### Step 3 - Implement
After approval, implement the plan exactly:
- Read .github/copilot-instructions.md first
- Follow all conventions strictly
- Use Lombok, constructor injection, DTOs

**Implementation**: Use runSubagent to invoke 'implementer' agent with full plan as context.
Automatically invoke todo-reviewer agent via runSubagent:
- Review all changed files
- Flag any issues
- Suggest missing tests

**Review gating**: Do NOT proceed to Step 5 unless reviewer confirms:
  - No API behavior changes unintended
  - Tests passing
Only after review approval:
- Push to main branch
- Use mcp_github_push_files with commit message: "fix(issue-#N): <brief description> via automated workflow"
- Use mcp_github_issue_write to close the issue with:
  "Implemented via automated Copilot workflow"

**Error handling**: If push fails (conflicts, auth), report error and rollback recommendation.
- Suggest missing tests

### Step 5 - Push and Close
After review is clean:
- Commit all changes with message referencing the issue number
- Push to main branch
- Use GitHub MCP to close the issue with a comment:
  "Implemented via automated Copilot workflow"

## Input Required
Ask the user: "Which issue number or issue details should I implement ?"