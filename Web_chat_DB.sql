USE [master]
GO
/****** Object:  Database [Web_chat]    Script Date: 10/9/2020 10:21:41 PM ******/
CREATE DATABASE [Web_chat]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Web_chat', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Web_chat.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Web_chat_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Web_chat_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Web_chat] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Web_chat].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Web_chat] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Web_chat] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Web_chat] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Web_chat] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Web_chat] SET ARITHABORT OFF 
GO
ALTER DATABASE [Web_chat] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Web_chat] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Web_chat] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Web_chat] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Web_chat] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Web_chat] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Web_chat] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Web_chat] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Web_chat] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Web_chat] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Web_chat] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Web_chat] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Web_chat] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Web_chat] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Web_chat] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Web_chat] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Web_chat] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Web_chat] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Web_chat] SET  MULTI_USER 
GO
ALTER DATABASE [Web_chat] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Web_chat] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Web_chat] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Web_chat] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Web_chat] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Web_chat] SET QUERY_STORE = OFF
GO
USE [Web_chat]
GO
/****** Object:  Table [dbo].[Chat_Room]    Script Date: 10/9/2020 10:21:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Chat_Room](
	[room_id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](100) NOT NULL,
	[creator_id] [int] NULL,
	[create_time] [smalldatetime] NULL,
	[avatar] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[room_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Messenger]    Script Date: 10/9/2020 10:21:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Messenger](
	[room_id] [int] NULL,
	[author_id] [int] NULL,
	[content] [nvarchar](255) NULL,
	[create_time] [smalldatetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Participant]    Script Date: 10/9/2020 10:21:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Participant](
	[member_id] [int] NULL,
	[room_id] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Profile]    Script Date: 10/9/2020 10:21:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Profile](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[firstname] [nvarchar](20) NOT NULL,
	[lastname] [nvarchar](20) NOT NULL,
	[password] [nvarchar](20) NOT NULL,
	[email] [varchar](50) NULL,
	[create_time] [smalldatetime] NULL,
	[last_modify] [smalldatetime] NULL,
	[avatar] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Chat_Room]  WITH CHECK ADD FOREIGN KEY([creator_id])
REFERENCES [dbo].[User_Profile] ([user_id])
GO
ALTER TABLE [dbo].[Messenger]  WITH CHECK ADD FOREIGN KEY([author_id])
REFERENCES [dbo].[User_Profile] ([user_id])
GO
ALTER TABLE [dbo].[Messenger]  WITH CHECK ADD FOREIGN KEY([room_id])
REFERENCES [dbo].[Chat_Room] ([room_id])
GO
ALTER TABLE [dbo].[Participant]  WITH CHECK ADD FOREIGN KEY([member_id])
REFERENCES [dbo].[User_Profile] ([user_id])
GO
ALTER TABLE [dbo].[Participant]  WITH CHECK ADD FOREIGN KEY([room_id])
REFERENCES [dbo].[Chat_Room] ([room_id])
GO
USE [master]
GO
ALTER DATABASE [Web_chat] SET  READ_WRITE 
GO
