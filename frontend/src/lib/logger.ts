/**
 * 前端日志工具
 * 提供统一的日志记录功能，支持不同级别的日志输出
 */

export enum LogLevel {
  ERROR = 'error',
  WARN = 'warn',
  INFO = 'info',
  DEBUG = 'debug',
  TRACE = 'trace',
}

export interface LogEntry {
  timestamp: string;
  level: LogLevel;
  message: string;
  data?: any;
  userId?: string;
  sessionId?: string;
  url?: string;
  userAgent?: string;
}

class Logger {
  private isDevelopment = process.env.NODE_ENV === 'development';
  private logLevel: LogLevel = this.isDevelopment ? LogLevel.DEBUG : LogLevel.INFO;

  private formatLog(level: LogLevel, message: string, data?: any): LogEntry {
    return {
      timestamp: new Date().toISOString(),
      level,
      message,
      data,
      userId: this.getUserId(),
      sessionId: this.getSessionId(),
      url: typeof window !== 'undefined' ? window.location.href : undefined,
      userAgent: typeof window !== 'undefined' ? navigator.userAgent : undefined,
    };
  }

  private getUserId(): string | undefined {
    if (typeof window === 'undefined') return undefined;
    try {
      const user = localStorage.getItem('user');
      return user ? JSON.parse(user).id : undefined;
    } catch {
      return undefined;
    }
  }

  private getSessionId(): string | undefined {
    if (typeof window === 'undefined') return undefined;
    try {
      return sessionStorage.getItem('sessionId') || undefined;
    } catch {
      return undefined;
    }
  }

  private shouldLog(level: LogLevel): boolean {
    const levels = [LogLevel.ERROR, LogLevel.WARN, LogLevel.INFO, LogLevel.DEBUG, LogLevel.TRACE];
    const currentLevelIndex = levels.indexOf(this.logLevel);
    const messageLevelIndex = levels.indexOf(level);
    return messageLevelIndex <= currentLevelIndex;
  }

  private writeToFile(logEntry: LogEntry): void {
    if (typeof window === 'undefined') return;
    
    try {
      // 发送到后端日志接口
      const apiUrl = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:9091/api';
      fetch(`${apiUrl}/logs`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(logEntry),
      }).catch(() => {
        // 静默失败，避免日志记录本身导致错误
      });
    } catch {
      // 静默失败
    }
  }

  private log(level: LogLevel, message: string, data?: any): void {
    if (!this.shouldLog(level)) return;

    const logEntry = this.formatLog(level, message, data);

    // 控制台输出
    const consoleMethod = level === LogLevel.ERROR ? 'error' :
                         level === LogLevel.WARN ? 'warn' :
                         level === LogLevel.INFO ? 'info' :
                         level === LogLevel.DEBUG ? 'log' : 'log';

    if (data) {
      console[consoleMethod](`[${level.toUpperCase()}] ${message}`, data);
    } else {
      console[consoleMethod](`[${level.toUpperCase()}] ${message}`);
    }

    // 写入文件
    this.writeToFile(logEntry);
  }

  error(message: string, data?: any): void {
    this.log(LogLevel.ERROR, message, data);
  }

  warn(message: string, data?: any): void {
    this.log(LogLevel.WARN, message, data);
  }

  info(message: string, data?: any): void {
    this.log(LogLevel.INFO, message, data);
  }

  debug(message: string, data?: any): void {
    this.log(LogLevel.DEBUG, message, data);
  }

  trace(message: string, data?: any): void {
    this.log(LogLevel.TRACE, message, data);
  }

  // 设置日志级别
  setLevel(level: LogLevel): void {
    this.logLevel = level;
  }

  // 获取当前日志级别
  getLevel(): LogLevel {
    return this.logLevel;
  }
}

// 创建单例实例
export const logger = new Logger();

// 导出默认实例
export default logger;

